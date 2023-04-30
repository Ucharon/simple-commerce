package asia.oxox.charon.simplecommerce;

import asia.oxox.charon.simplecommerce.entity.DO.User;
import asia.oxox.charon.simplecommerce.service.RedisService;
import asia.oxox.charon.simplecommerce.service.UserService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static asia.oxox.charon.simplecommerce.constants.RedisConstants.USER_BALANCE_KEY;

/**
 * @program: simple-commerce
 * @description:
 * @author: Charon
 * @create: 2023-04-30 02:42
 **/
@SpringBootTest
public class UserTest {

    @Resource
    private UserService userService;
    @Resource
    private RedisService redisService;

    /**
     * 用户余额缓存预热
     */
    @Test
    void initAllUserBalance() {
        List<User> userList = userService.list();
        Map<Long, BigDecimal> map = userList.stream().collect(Collectors.toMap(User::getId, User::getBalance));
        map.forEach((id, bigDecimal) -> {
            String key = USER_BALANCE_KEY + id;
            redisService.set(key, bigDecimal);
        });
    }
}
