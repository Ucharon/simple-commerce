package asia.oxox.charon.simplecommerce.constants;

/**
 * @program: simple-frame
 * @description:
 * @author: Charon
 * @create: 2023-04-09 20:43
 **/
public class RedisConstants {

    public static final String LOGIN_USER_KEY = "login:token:";
    public static final Long LOGIN_USER_TTL = 600000L;
    //public static final Long LOGIN_USER_TTL = 120L;

    public static final String CACHE_USER_KEY = "cache:user:";
    public static final Long CACHE_USER_TTL = 120L;
    public static final String CACHE_GOODS_KEY = "cache:goods:";
    public static final String LOCK_GOODS_KEY = "lock:goods:";
    public static final Long CACHE_GOODS_TTL = 120L;

    public static final String USER_BALANCE_KEY = "user:balance:";

    public static final String GOODS_STOCK_KEY = "goods:stock:";

    public static final Long CACHE_NULL_TTL = 120L;

    public static final String LOCK_ORDER_KEY = "lock:order:";

}
