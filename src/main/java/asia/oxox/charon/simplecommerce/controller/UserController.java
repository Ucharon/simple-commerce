package asia.oxox.charon.simplecommerce.controller;

import asia.oxox.charon.simplecommerce.entity.DTO.BalanceDetailPageDto;
import asia.oxox.charon.simplecommerce.entity.DTO.UserLoginDto;
import asia.oxox.charon.simplecommerce.entity.VO.PageVo;
import asia.oxox.charon.simplecommerce.entity.VO.Result;
import asia.oxox.charon.simplecommerce.entity.VO.UserInfoVo;
import asia.oxox.charon.simplecommerce.service.BalanceDetailService;
import asia.oxox.charon.simplecommerce.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    private BalanceDetailService balanceDetailService;

    @ApiOperation(value = "登录操作")
    @PostMapping("/login")
    public Result login(@RequestBody @Validated UserLoginDto userDto) {
        Map<String, String> res = userService.login(userDto);
        return Result.success(res);
    }

    @ApiOperation("获取个人信息")
    @GetMapping("/info")
    public Result getInfo() {
        UserInfoVo userInfoVo = userService.getInfo();
        return Result.success(userInfoVo);
    }

    @ApiOperation("获取个人余额变动消息")
    @PostMapping("/balance/detail/page")
    public Result balanceDetailPage(@RequestBody BalanceDetailPageDto balanceDetailDto) {
        PageVo<?> pageVo = balanceDetailService.balanceDetailPage(balanceDetailDto);
        return Result.success(pageVo);
    }
}
