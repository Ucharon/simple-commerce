package asia.oxox.charon.simplecommerce.controller;

import asia.oxox.charon.simplecommerce.entity.DTO.GoodsDto;
import asia.oxox.charon.simplecommerce.entity.VO.Result;
import asia.oxox.charon.simplecommerce.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: simple-commerce
 * @description: 订单相关的控制器
 * @author: Charon
 * @create: 2023-04-30 00:26
 **/
@Api(tags = "订单接口", value = "订单的具体操作")
@RestController
@AllArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private GoodsService goodsService;

    @ApiOperation(value = "新增商品")
    @PostMapping("/login")
    public Result login(@RequestBody @Validated GoodsDto goodsDto) {
        goodsService.saveGoods(goodsDto);
        return Result.success();
    }


}
