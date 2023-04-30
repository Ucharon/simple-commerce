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
    SERVICE_BUSY(503, "服务器繁忙，请稍后再试"),

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
     * 商品相关
     */
    GOODS_NOT_EXIST(1000, "商品不存在"),
    PURCHASE_FAILURE(1001, "您的余额或商品库存不足"),
    ORDER_DOES_NOT_EXIST(1002, "订单不存在"),
    ;

    //状态码
    private final Integer code;
    //描述
    private final String desc;
}
