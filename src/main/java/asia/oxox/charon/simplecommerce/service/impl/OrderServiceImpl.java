package asia.oxox.charon.simplecommerce.service.impl;

import asia.oxox.charon.simplecommerce.entity.DO.Goods;
import asia.oxox.charon.simplecommerce.entity.DO.Order;
import asia.oxox.charon.simplecommerce.entity.DO.User;
import asia.oxox.charon.simplecommerce.entity.mq.BalanceUpdateDto;
import asia.oxox.charon.simplecommerce.entity.mq.GoodsOrderDto;
import asia.oxox.charon.simplecommerce.enums.OrderStatusEnum;
import asia.oxox.charon.simplecommerce.enums.ResultCodeEnum;
import asia.oxox.charon.simplecommerce.exception.BizException;
import asia.oxox.charon.simplecommerce.mapper.OrderMapper;
import asia.oxox.charon.simplecommerce.service.GoodsService;
import asia.oxox.charon.simplecommerce.service.OrderService;
import asia.oxox.charon.simplecommerce.service.RedisService;
import asia.oxox.charon.simplecommerce.service.UserService;
import asia.oxox.charon.simplecommerce.utils.ArithmeticUtils;
import asia.oxox.charon.simplecommerce.utils.RedisIdGenerator;
import asia.oxox.charon.simplecommerce.utils.TimeUtils;
import asia.oxox.charon.simplecommerce.utils.UserHolder;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xin.altitude.cms.common.util.BeanCopyUtils;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static asia.oxox.charon.simplecommerce.constants.MQPrefixConstants.*;
import static asia.oxox.charon.simplecommerce.constants.RedisConstants.*;

/**
 * @author charon
 * @description 针对表【tb_order】的数据库操作Service实现
 * @createDate 2023-04-30 01:47:38
 */
@Service
@AllArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
        implements OrderService {

    private RedisIdGenerator redisIdGenerator;
    private RedisService redisService;
    private RabbitTemplate rabbitTemplate;
    private GoodsService goodsService;
    private UserService userService;
    private RedissonClient redissonClient;

    private static final DefaultRedisScript<Long> ORDER_SCRIPT;

    static {
        ORDER_SCRIPT = new DefaultRedisScript<>();
        ORDER_SCRIPT.setLocation(new ClassPathResource("order.lua"));
        ORDER_SCRIPT.setResultType(Long.class);
    }

    @Override
    public Long orderGoods(Long goodsId) {
        //1. 获取用户与商品信息
        User user = UserHolder.getUser();
        Goods goods = Optional.ofNullable(goodsService.getGoodsById(goodsId))
                .orElseThrow(() -> new BizException(ResultCodeEnum.GOODS_NOT_EXIST));
        //2. 获取订单id
        Long orderId = redisIdGenerator.nextId("order");
        //3. 执行lua脚本
        Long res = redisService.execute(
                ORDER_SCRIPT,
                goodsId.toString(),
                user.getId().toString(),
                goods.getPrice()
        );
        //4. 判断结果
        if (res != 0) {
            //4.1 不为0，下单失败
            throw new BizException(ResultCodeEnum.PURCHASE_FAILURE);
        }
        //4.2 下单成功，创建消息队列
        rabbitTemplate.convertAndSend(ORDER_EVENT_EXCHANGE, ORDER_CREATE_QUEUE,
                GoodsOrderDto.builder().id(orderId)
                        .goodsId(goodsId)
                        .userId(user.getId())
                        .price(goods.getPrice())
                        .build());

        //4.3 这个消息队列用于更新余额变动明细和扣减数据库的余额
        rabbitTemplate.convertAndSend(BALANCE_EVENT_EXCHANGE, BALANCE_CHANGE_QUEUE,
                BalanceUpdateDto.builder()
                        .userId(user.getId())
                        .orderId(orderId)
                        .changeBalance(ArithmeticUtils.sub("0.00", goods.getPrice().toString()))
                        .statusEnum(OrderStatusEnum.COMPLETED.getStatusCode())
                        .build()
        );


        return orderId;
    }

    @Override
    @Transactional
    public void createOrder(GoodsOrderDto goodsOrderDto) {
        //1. 扣减库存
        goodsService.lambdaUpdate()
                .eq(Goods::getId, goodsOrderDto.getGoodsId())
                .setSql("stock=stock-1")
                .update();

        //2. 初始化订单状态
        Order order = BeanCopyUtils.copyProperties(goodsOrderDto, Order.class);
        order.setStatusEnum(OrderStatusEnum.COMPLETED);

        //3. 创建订单
        save(order);
    }

    @Override
    public void refundOrder(Long orderId) throws InterruptedException {
        //0. 判断该订单是否存在
        Order order = Optional.ofNullable(getById(getById(orderId)))
                .orElseThrow(() -> new BizException(ResultCodeEnum.ORDER_DOES_NOT_EXIST));

        //1. 创建锁对象，并获取锁
        RLock lock = redissonClient
                .getLock(LOCK_ORDER_KEY + orderId);
        boolean isLock = lock
                .tryLock(LOCK_ORDER_TTL, TimeUnit.SECONDS);
        //2. 判断锁是否获取成功
        if (!isLock) {
            //2.1 获取失败，提醒用户稍后再试
            throw new BizException(ResultCodeEnum.SERVICE_BUSY);
        }
        //2.2获取锁成功
        //3. 开始执行退款业务
        try {
            //3.1 修改订单信息，并存入数据库
            order.setStatusEnum(OrderStatusEnum.REFUNDED);
            order.setRefundTime(TimeUtils.getCurrentTime());
            updateById(order);

            //3.2 余额退回，更新redis即可
            String balanceKey = USER_BALANCE_KEY + order.getUserId();
            BigDecimal balance = (BigDecimal) redisService.get(balanceKey);
            redisService.set(balanceKey, ArithmeticUtils.add(balance.toString(), order.getPrice().toString()));

            //3.3 这个消息队列用于更新余额变动明细和扣减数据库的余额
            rabbitTemplate.convertAndSend(BALANCE_EVENT_EXCHANGE, BALANCE_CHANGE_QUEUE,
                    BalanceUpdateDto.builder()
                            .userId(order.getUserId())
                            .orderId(orderId)
                            .changeBalance(order.getPrice())
                            .statusEnum(OrderStatusEnum.REFUNDED.getStatusCode())
                            .build()
            );
        } finally {
            //最后释放锁
            lock.unlock();
        }


    }

}




