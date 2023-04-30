package asia.oxox.charon.simplecommerce.entity.DTO;

import asia.oxox.charon.simplecommerce.enums.OrderStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: simple-commerce
 * @description:
 * @author: Charon
 * @create: 2023-04-30 19:18
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("余额变动DTO")
public class BalanceDetailPageDto extends PageDto {

    @ApiModelProperty(value = "变动原因状态")
    private OrderStatusEnum statusEnum;
}
