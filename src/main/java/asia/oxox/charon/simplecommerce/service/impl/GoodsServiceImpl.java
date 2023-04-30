package asia.oxox.charon.simplecommerce.service.impl;

import asia.oxox.charon.simplecommerce.entity.DO.Goods;
import asia.oxox.charon.simplecommerce.entity.DTO.GoodsDto;
import asia.oxox.charon.simplecommerce.mapper.GoodsMapper;
import asia.oxox.charon.simplecommerce.service.GoodsService;
import asia.oxox.charon.simplecommerce.service.RedisService;
import asia.oxox.charon.simplecommerce.utils.CacheClient;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xin.altitude.cms.bitmap.annotation.BitMap;
import xin.altitude.cms.common.util.BeanCopyUtils;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import static asia.oxox.charon.simplecommerce.constants.RedisConstants.*;

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
    private CacheClient cacheClient;

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

    @Override
    public Goods getGoodsById(Long goodsId) {
        Goods goods = cacheClient.queryWithMutex(CACHE_GOODS_KEY, LOCK_GOODS_KEY,
                goodsId, Goods.class, this::getById,
                CACHE_GOODS_TTL, TimeUnit.HOURS);
        Long stock = (Long) redisService.get(GOODS_STOCK_KEY + goods.getId());
        goods.setStock(stock);
        return goods;
    }
}




