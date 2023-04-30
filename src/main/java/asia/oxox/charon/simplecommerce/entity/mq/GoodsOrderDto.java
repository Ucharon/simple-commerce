package asia.oxox.charon.simplecommerce.entity.mq;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: simple-commerce
 * @description:
 * @author: Charon
 * @create: 2023-04-30 03:10
 **/
@Data
@Builder
public class GoodsOrderDto {

    private Long orderId;
    private Long goodsId;
    private Long userId;
    private BigDecimal price;
}
