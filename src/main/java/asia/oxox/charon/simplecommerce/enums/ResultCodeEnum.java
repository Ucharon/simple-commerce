package asia.oxox.charon.simplecommerce.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 返回结果枚举
 */
@Getter
@AllArgsConstructor
public enum ResultCodeEnum {

    SUCCESS(200, "成功"),
    INTERNAL_SERVER_ERROR(500, "未知异常"),
    REQUEST_METHOD_ILLEGAL(502, "请求类型错误"),

    /**
     * 用户
     */
    LOGIN_ERROR(600, "用户名或密码错误"),

    /**
     * 参数校验
     */
    ERROR_PARAM(900, "参数错误"),
    ILLEGAL_FORMAT(901, "输入格式错误"),

    /**
     * 购买商品
     */
    PURCHASE_FAILURE(1000, "您的余额或商品库存不足");

    //状态码
    private final Integer code;
    //描述
    private final String desc;
}
