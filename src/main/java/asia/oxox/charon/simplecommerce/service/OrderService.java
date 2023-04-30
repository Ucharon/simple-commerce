package asia.oxox.charon.simplecommerce.service;

import asia.oxox.charon.simplecommerce.entity.DO.Order;
import asia.oxox.charon.simplecommerce.entity.mq.GoodsOrderDto;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author charon
* @description 针对表【tb_order】的数据库操作Service
* @createDate 2023-04-30 01:47:38
*/
public interface OrderService extends IService<Order> {

    Long orderGoods(Long goodsId);

    void createOrder(GoodsOrderDto goodsOrderDto);

    void refundOrder(Long orderId) throws InterruptedException;
}
