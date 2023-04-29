package asia.oxox.charon.simplecommerce.service.impl;

import asia.oxox.charon.simplecommerce.entity.DTO.GoodsDto;
import asia.oxox.charon.simplecommerce.service.RedisService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import asia.oxox.charon.simplecommerce.entity.DO.Goods;
import asia.oxox.charon.simplecommerce.service.GoodsService;
import asia.oxox.charon.simplecommerce.mapper.GoodsMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xin.altitude.cms.common.util.BeanCopyUtils;

import static asia.oxox.charon.simplecommerce.constants.RedisConstants.GOODS_STOCK_KEY;

/**
 * @author charon
 * @description 针对表【tb_goods】的数据库操作Service实现
 * @createDate 2023-04-30 01:15:20
 */
@Service
@AllArgsConstructor
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods>
        implements GoodsService {

    private RedisService redisService;

    /**
     * 保存商品，并将库存存入redis
     *
     * @param goodsDto
     */
    @Override
    public void saveGoods(GoodsDto goodsDto) {
        Goods goods = BeanCopyUtils.copyProperties(goodsDto, Goods.class);
        //1. 保存商品信息
        save(goods);
        //2. 保存库存到redis中
        redisService.set(GOODS_STOCK_KEY + goods.getId(), goods.getStock());
    }
}




