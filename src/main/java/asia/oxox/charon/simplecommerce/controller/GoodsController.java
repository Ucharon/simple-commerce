package asia.oxox.charon.simplecommerce.controller;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
