package asia.oxox.charon.simplecommerce.controller;

import asia.oxox.charon.simplecommerce.entity.DTO.UserLoginDto;
import asia.oxox.charon.simplecommerce.entity.VO.Result;
import asia.oxox.charon.simplecommerce.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @program: simple-commerce
 * @description: 用户控制器
 * @author: Charon
 * @create: 2023-04-29 22:03
 **/
@Api(tags = "用户接口", value = "用户的具体操作")
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    /**
     * 登录接口
     *
     * @param userDto
     * @return
     */
    @ApiOperation(value = "登录操作")
    @PostMapping("/login")
    public Result login(@RequestBody @Validated UserLoginDto userDto) {
        Map<String, String> res = userService.login(userDto);
        return Result.success(res);
    }
}
