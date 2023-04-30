package asia.oxox.charon.simplecommerce.service.impl;

import asia.oxox.charon.simplecommerce.entity.DO.Goods;
import asia.oxox.charon.simplecommerce.entity.DO.User;
import asia.oxox.charon.simplecommerce.entity.mq.GoodsOrderDto;
import asia.oxox.charon.simplecommerce.enums.OrderStatusEnum;
import asia.oxox.charon.simplecommerce.enums.ResultCodeEnum;
import asia.oxox.charon.simplecommerce.exception.BizException;
import asia.oxox.charon.simplecommerce.service.GoodsService;
import asia.oxox.charon.simplecommerce.service.RedisService;
import asia.oxox.charon.simplecommerce.service.UserService;
import asia.oxox.charon.simplecommerce.utils.RedisIdGenerator;
import asia.oxox.charon.simplecommerce.utils.UserHolder;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import asia.oxox.charon.simplecommerce.entity.DO.Order;
import asia.oxox.charon.simplecommerce.service.OrderService;
import asia.oxox.charon.simplecommerce.mapper.OrderMapper;
import lombok.AllArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xin.altitude.cms.common.util.BeanCopyUtils;

import java.util.Optional;

import static asia.oxox.charon.simplecommerce.constants.MQPrefixConstants.ORDER_CREATE_QUEUE;
import static asia.oxox.charon.simplecommerce.constants.MQPrefixConstants.ORDER_EVENT_EXCHANGE;
import static asia.oxox.charon.simplecommerce.constants.RedisConstants.LOCK_ORDER_KEY;

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

        //2. 扣减余额
        userService.lambdaUpdate()
                .eq(User::getId, goodsOrderDto.getUserId())
                .setSql("balance=balance-" + goodsOrderDto.getPrice())
                .update();

        //3. 初始化订单状态
        Order order = BeanCopyUtils.copyProperties(goodsOrderDto, Order.class);
        order.setStatusEnum(OrderStatusEnum.COMPLETED);

        //4. 创建订单
        save(order);
    }

}




