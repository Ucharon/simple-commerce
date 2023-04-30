package asia.oxox.charon.simplecommerce.service;

import asia.oxox.charon.simplecommerce.entity.DO.User;
import asia.oxox.charon.simplecommerce.entity.DTO.UserLoginDto;
import asia.oxox.charon.simplecommerce.entity.VO.UserInfoVo;
import asia.oxox.charon.simplecommerce.entity.mq.BalanceUpdateDto;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
* @author charon
* @description 针对表【sys_user】的数据库操作Service
* @createDate 2023-04-29 22:02:07
*/
public interface UserService extends IService<User> {

    Map<String, String> login(UserLoginDto userDto);

    UserInfoVo getInfo();

    void updateBalance(BalanceUpdateDto balanceUpdateDto);
}
