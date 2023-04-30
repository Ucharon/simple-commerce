package asia.oxox.charon.simplecommerce.entity.mq;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @program: simple-commerce
 * @description:
 * @author: Charon
 * @create: 2023-04-30 03:10
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsOrderDto {

    private Long orderId;
    private Long goodsId;
    private Long userId;
    private BigDecimal price;
}
