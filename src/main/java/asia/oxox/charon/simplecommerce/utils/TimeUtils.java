package asia.oxox.charon.simplecommerce.utils;


import asia.oxox.charon.simplecommerce.enums.ZoneEnum;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @program: graduate-website
 * @description:
 * @author: Charon
 * @create: 2023-03-31 20:18
 **/
public class TimeUtils {

    public static LocalDateTime getCurrentTime() {
        return LocalDateTime.now(ZoneId.of(ZoneEnum.SHANGHAI.getZone()));
    }
}
