package asia.oxox.charon.simplecommerce;

import asia.oxox.charon.simplecommerce.service.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @program: simple-commerce
 * @description:
 * @author: Charon
 * @create: 2023-04-30 10:34
 **/
@SpringBootTest
public class LuaTest {

    @Resource
    private RedisService redisService;


    /**
     * 测试下通过键获取redis中数据的情况
     */
    @Test
    void testRedisgetByKey() {
        final DefaultRedisScript<BigDecimal> ORDER_SCRIPT;
        ORDER_SCRIPT = new DefaultRedisScript<>();
        ORDER_SCRIPT.setLocation(new ClassPathResource("test.lua"));
        ORDER_SCRIPT.setResultType(BigDecimal.class);
        BigDecimal execute = redisService.execute(
                ORDER_SCRIPT
                //Long.toString(11L),
                //Long.toString(1L),
                //new BigDecimal("10.11")
        );
        System.out.println(execute);
    }
}
