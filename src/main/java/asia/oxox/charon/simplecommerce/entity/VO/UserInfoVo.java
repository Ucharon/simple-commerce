package asia.oxox.charon.simplecommerce.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: simple-commerce
 * @description:
 * @author: Charon
 * @create: 2023-04-29 23:19
 **/
@Data
@ApiModel("个人信息数据结构")
public class UserInfoVo {

    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("余额")
    private BigDecimal balance;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("手机号")
    private String phonenumber;
}
