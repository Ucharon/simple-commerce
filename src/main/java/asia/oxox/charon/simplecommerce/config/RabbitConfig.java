package asia.oxox.charon.simplecommerce.config;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static asia.oxox.charon.simplecommerce.constants.MQPrefixConstants.ORDER_EVENT_EXCHANGE;
import static asia.oxox.charon.simplecommerce.constants.MQPrefixConstants.ORDER_CREATE_QUEUE;


/**
 * @program: simple-commerce
 * @description: RabbitMQ配置
 * @author: Charon
 * @create: 2023-04-30 02:19
 **/
@Configuration
@AllArgsConstructor
public class RabbitConfig {

    /**
     * 使用JSON序列化机制，进行消息转换
     * @return
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public FanoutExchange orderExchange() {
        return new FanoutExchange(ORDER_EVENT_EXCHANGE, true, false);
    }

    @Bean
    public Queue orderCreateQueue() {
        return new Queue(ORDER_CREATE_QUEUE, true, false, false);
    }

    @Bean
    public Binding orderCreateQueueBinding() {
        return new Binding(ORDER_CREATE_QUEUE,
                Binding.DestinationType.QUEUE,
                ORDER_EVENT_EXCHANGE,
                ORDER_CREATE_QUEUE,
                null);
    }


}
