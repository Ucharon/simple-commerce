package asia.oxox.charon.simplecommerce.service;

import asia.oxox.charon.simplecommerce.entity.DO.Goods;
import asia.oxox.charon.simplecommerce.entity.DTO.GoodsDto;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author charon
* @description 针对表【tb_goods】的数据库操作Service
* @createDate 2023-04-30 01:15:20
*/
public interface GoodsService extends IService<Goods> {

    void saveGoods(GoodsDto goodsDto);

    Goods getGoodsById(Long goodsId);
}
