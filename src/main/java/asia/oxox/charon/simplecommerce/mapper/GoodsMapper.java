package asia.oxox.charon.simplecommerce.mapper;

import asia.oxox.charon.simplecommerce.entity.DO.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author charon
* @description 针对表【tb_goods】的数据库操作Mapper
* @createDate 2023-04-30 01:15:20
* @Entity asia.oxox.charon.simplecommerce.entity.DO.Goods
*/
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

}




