package asia.oxox.charon.simplecommerce.exception;

import asia.oxox.charon.simplecommerce.enums.ResultCodeEnum;
import lombok.Getter;

/**
 * @program: recommend-website
 * @description: 统一业务异常类
 * @author: Charon
 * @create: 2022-12-09 13:30
 **/
@Getter
public class BizException extends RuntimeException {

    private final ResultCodeEnum error;

    /**
     * 构造器，有时我们需要将第三方异常转为自定义异常抛出，但又不想丢失原来的异常信息，此时可以传入cause
     *
     * @param error
     * @param cause
     */
    public BizException(ResultCodeEnum error, Throwable cause) {
        super(cause);
        this.error = error;
    }

    /**
     * 构造器，只传入错误枚举
     *
     * @param error
     */
    public BizException(ResultCodeEnum error) {
        this.error = error;
    }
}
