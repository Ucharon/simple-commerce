package asia.oxox.charon.simplecommerce.mapper;

import asia.oxox.charon.simplecommerce.entity.DO.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author charon
* @description 针对表【sys_user】的数据库操作Mapper
* @createDate 2023-04-29 22:02:07
* @Entity asia.oxox.charon.simplecommerce.entity.DO.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




