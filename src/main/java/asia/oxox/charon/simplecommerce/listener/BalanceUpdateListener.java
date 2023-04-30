package asia.oxox.charon.simplecommerce.listener;

import asia.oxox.charon.simplecommerce.entity.mq.BalanceUpdateDto;
import asia.oxox.charon.simplecommerce.entity.mq.GoodsOrderDto;
import asia.oxox.charon.simplecommerce.service.OrderService;
import asia.oxox.charon.simplecommerce.service.UserService;
import com.rabbitmq.client.Channel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static asia.oxox.charon.simplecommerce.constants.MQPrefixConstants.BALANCE_CHANGE_QUEUE;

/**
 * @program: simple-commerce
 * @description: 余额变动listener
 * @author: Charon
 * @create: 2023-04-30 17:59
 **/
@RabbitListener(queues = BALANCE_CHANGE_QUEUE)
@Component
@AllArgsConstructor
@Slf4j
public class BalanceUpdateListener {

    private UserService userService;

    @RabbitHandler
    public void listener(BalanceUpdateDto balanceUpdateDto, Channel channel, Message message) throws IOException {
        log.info("准备更新用户的余额信息......");
        try {
            userService.updateBalance(balanceUpdateDto);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
            log.error(e.getMessage());
        }
    }


}
