package asia.oxox.charon.simplecommerce.mapper;

import asia.oxox.charon.simplecommerce.entity.DO.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author charon
* @description 针对表【tb_order】的数据库操作Mapper
* @createDate 2023-04-30 01:47:38
* @Entity asia.oxox.charon.simplecommerce.entity.DO.Order
*/
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}




