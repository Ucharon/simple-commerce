package asia.oxox.charon.simplecommerce.entity.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: simple-commerce
 * @description:
 * @author: Charon
 * @create: 2023-04-30 01:17
 **/
@Data
@ApiModel("商品DTO")
public class GoodsDto {
    @ApiModelProperty(value = "商品名称")
    private String details;
    @ApiModelProperty(value = "详细信息")
    private String name;
    @ApiModelProperty(value = "价格")
    private BigDecimal price;
    @ApiModelProperty(value = "库存")
    private Long stock;
    @ApiModelProperty(value = "缩略图地址")
    private String thumbnail;
}
