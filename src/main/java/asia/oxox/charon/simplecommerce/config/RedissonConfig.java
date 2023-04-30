package asia.oxox.charon.simplecommerce.config;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: simple-commerce
 * @description: redisson配置
 * @author: Charon
 * @create: 2023-04-30 18:40
 **/
@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private String port;
    @Value("${spring.redis.password}")
    private String password;

    @Bean
    public RedissonClient redissonClient() {
        //配置
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer().setAddress("redis://" + host + ":" + port);
        if (StringUtils.isNotBlank(password)) {
            singleServerConfig.setPassword(password);
        }
        //创建对并且返回
        return Redisson.create(config);
    }

}
