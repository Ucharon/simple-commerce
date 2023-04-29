package asia.oxox.charon.simplecommerce.controller;

import asia.oxox.charon.simplecommerce.entity.DO.User;
import asia.oxox.charon.simplecommerce.entity.VO.UserInfoVo;
import asia.oxox.charon.simplecommerce.entity.DTO.UserLoginDto;
import asia.oxox.charon.simplecommerce.entity.VO.Result;
import asia.oxox.charon.simplecommerce.service.UserService;
import asia.oxox.charon.simplecommerce.utils.UserHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xin.altitude.cms.common.util.BeanCopyUtils;

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

    @ApiOperation("获取个人信息")
    @GetMapping("/info")
    public Result getInfo() {
        return Result.success(BeanCopyUtils.copyProperties(UserHolder.getUser(), UserInfoVo.class));
    }
}
