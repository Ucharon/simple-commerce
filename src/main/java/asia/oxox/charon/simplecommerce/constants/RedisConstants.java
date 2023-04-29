package asia.oxox.charon.simplecommerce.constants;

/**
 * @program: simple-frame
 * @description:
 * @author: Charon
 * @create: 2023-04-09 20:43
 **/
public class RedisConstants {

    public static final String LOGIN_CODE_KEY = "login:code:";
    public static final Long LOGIN_CODE_TTL = 2L;
    public static final String LOGIN_USER_KEY = "login:token:";
    //public static final Long LOGIN_USER_TTL = 600000L;
    public static final Long LOGIN_USER_TTL = 120L;

    public static final String CACHE_USER_KEY = "cache:user:";
    public static final Long CACHE_USER_TTL = 120L;

}
