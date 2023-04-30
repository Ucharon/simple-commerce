package asia.oxox.charon.simplecommerce.entity.mq;

import asia.oxox.charon.simplecommerce.enums.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @program: simple-commerce
 * @description:
 * @author: Charon
 * @create: 2023-04-30 18:09
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BalanceUpdateDto {

    private Long userId;
    private Long orderId;
    private BigDecimal changeBalance;
    private Integer statusEnum;
}
