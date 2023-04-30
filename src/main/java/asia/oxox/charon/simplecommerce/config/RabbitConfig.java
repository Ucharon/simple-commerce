package asia.oxox.charon.simplecommerce.config;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static asia.oxox.charon.simplecommerce.constants.MQPrefixConstants.ORDER_EXCHANGE;
import static asia.oxox.charon.simplecommerce.constants.MQPrefixConstants.ORDER_QUEUE;


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
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public Queue orderQueue() {
        return new Queue(ORDER_QUEUE, true);
    }

    @Bean
    public FanoutExchange orderExchange() {
        return new FanoutExchange(ORDER_EXCHANGE, true, false);
    }



}
