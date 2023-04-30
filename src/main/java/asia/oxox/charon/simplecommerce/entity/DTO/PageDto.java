package asia.oxox.charon.simplecommerce.entity.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: graduate-website
 * @description: 分页基本信息
 * @author: Charon
 * @create: 2023-03-28 16:36
 **/
@Data
@ApiModel("分页DTO")
public class PageDto {

    @NotNull(message = "分页信息为空")
    @ApiModelProperty(value = "变动原因状态")
    private Integer pageNum;
    @NotNull(message = "分页信息为空")
    @ApiModelProperty(value = "变动原因状态")
    private Integer pageSize;

}
