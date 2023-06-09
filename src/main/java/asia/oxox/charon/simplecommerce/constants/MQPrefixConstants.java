package asia.oxox.charon.simplecommerce.constants;

/**
 * @program: simple-commerce
 * @description: mq常量
 * @author: Charon
 * @create: 2023-04-30 02:26
 **/
public class MQPrefixConstants {

    /**
     * 订单消息队列交换机
     */
    public static final String ORDER_EVENT_EXCHANGE = "order_event_exchange";

    /**
     * 订单创建消息队列
     */
    public static final String ORDER_CREATE_QUEUE = "order_create_queue";


    /**
     * 余额消息队列交换机
     */
    public static final String BALANCE_EVENT_EXCHANGE = "balance_event_exchange";

    /**
     * 余额变动消息队列
     */
    public static final String BALANCE_CHANGE_QUEUE = "balance_change_queue";


}
