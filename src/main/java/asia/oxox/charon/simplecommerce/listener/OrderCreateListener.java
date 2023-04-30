package asia.oxox.charon.simplecommerce.listener;

import asia.oxox.charon.simplecommerce.entity.mq.GoodsOrderDto;
import asia.oxox.charon.simplecommerce.service.OrderService;
import com.rabbitmq.client.Channel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.stereotype.Component;


import static asia.oxox.charon.simplecommerce.constants.MQPrefixConstants.ORDER_CREATE_QUEUE;

/**
 * @program: simple-commerce
 * @description: 订单创建listener
 * @author: Charon
 * @create: 2023-04-30 11:57
 **/
@RabbitListener(queues = ORDER_CREATE_QUEUE)
@Component
@AllArgsConstructor
@Slf4j
public class OrderCreateListener {

    private OrderService orderService;

    @RabbitHandler
    public void listener(GoodsOrderDto goodsOrderDto, Channel channel, Message message) {
        log.info("准备创建订单的详细信息......");

        try {
            orderService.createOrder(goodsOrderDto);
        } catch (Exception e) {

        }
    }

}
