package asia.oxox.charon.simplecommerce.controller;

import asia.oxox.charon.simplecommerce.entity.VO.Result;
import asia.oxox.charon.simplecommerce.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    private OrderService orderService;

    @ApiOperation(value = "购买商品")
    @PostMapping("/{goodsId}")
    public Result orderGoods(@ApiParam(name = "商品id") @PathVariable Long goodsId) {
        Long orderId = orderService.orderGoods(goodsId);
        return Result.success().put("orderId", orderId);
    }

}
