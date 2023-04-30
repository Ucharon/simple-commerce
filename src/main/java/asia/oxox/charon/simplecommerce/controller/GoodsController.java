package asia.oxox.charon.simplecommerce.controller;

import asia.oxox.charon.simplecommerce.entity.DO.Goods;
import asia.oxox.charon.simplecommerce.entity.DTO.GoodsDto;
import asia.oxox.charon.simplecommerce.entity.VO.Result;
import asia.oxox.charon.simplecommerce.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @program: simple-commerce
 * @description: 商品控制器
 * @author: Charon
 * @create: 2023-04-30 01:13
 **/
@Api(tags = "商品接口", value = "商品的具体操作")
@RestController
@AllArgsConstructor
@RequestMapping("/goods")
public class GoodsController {
    private GoodsService goodsService;

    @ApiOperation(value = "新增商品")
    @PostMapping("/login")
    public Result login(@RequestBody @Validated GoodsDto goodsDto) {
        goodsService.saveGoods(goodsDto);
        return Result.success();
    }

    @ApiOperation(value = "根据id查询商品")
    @GetMapping("/{goodsId}")
    public Result getGoodsById(@ApiParam(name = "商品id") @PathVariable Long goodsId) {
        Goods goods = goodsService.getGoodsById(goodsId);
        return Result.success(goods);
    }
}
