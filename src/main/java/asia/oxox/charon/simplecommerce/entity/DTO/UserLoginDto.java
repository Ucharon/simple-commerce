package asia.oxox.charon.simplecommerce.entity.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static asia.oxox.charon.simplecommerce.constants.RegexPatterns.PASSWORD_REGEX;

/**
 * @program: simple-commerce
 * @description:
 * @author: Charon
 * @create: 2023-04-29 22:04
 **/
@Data
@ApiModel("用户登录DTO")
public class UserLoginDto {

    @ApiModelProperty(value = "用户名", example = "zhangsan")
    @NotNull(message = "用户名不能为空")
    private String userName;

    @ApiModelProperty(value = "密码", example = "1234")
    @NotNull(message = "密码不能为空")
    @Pattern(regexp = PASSWORD_REGEX, message = "密码必须是4~32位的字母、数字、下划线!")
    private String password;
}

