package asia.oxox.charon.simplecommerce.handler;

import asia.oxox.charon.simplecommerce.entity.VO.Result;
import asia.oxox.charon.simplecommerce.enums.ResultCodeEnum;
import asia.oxox.charon.simplecommerce.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static asia.oxox.charon.simplecommerce.enums.ResultCodeEnum.*;

/**
 * @program: recommend-website
 * @description: 全局异常处理器
 * @author: Charon
 * @create: 2022-12-09 13:32
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 字段校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        //从异常对象中拿到ObjectError对象
        String defaultMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return Result.error(ILLEGAL_FORMAT, defaultMessage);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public Result handleConstraintViolationException(ConstraintViolationException ex) {
        //从异常对象中拿到ObjectError对象
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        Iterator<ConstraintViolation<?>> iterator = constraintViolations.iterator();
        List<String> msgList = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<?> cvl = iterator.next();
            msgList.add(cvl.getMessageTemplate());
        }
        return Result.error(ERROR_PARAM, msgList.toString());
    }

    /**
     * 处理Get请求中 使用@Valid 验证路径中请求实体校验失败后抛出的异常，详情继续往下看代码
     *
     * @Validated @Valid仅对于表单提交有效，对于以json格式提交将会失效）
     */
    @ExceptionHandler(BindException.class)
    public Result BindExceptionHandler(BindException e) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        List<String> msgList = new ArrayList<>();
        for (ObjectError allError : allErrors) {
            msgList.add(allError.getDefaultMessage());
        }
        return Result.error(ERROR_PARAM, msgList.get(0));
    }

    /**
     * 业务异常统一处理
     */
    @ExceptionHandler(BizException.class)
    public Result handleBizException(BizException e) {
        log.info("exception             : {}", e.getError().getDesc());
        return Result.error(e.getError());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return Result.error(REQUEST_METHOD_ILLEGAL);
    }

    /**
     * 处理其他异常
     */
    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception e) {
        log.error("未知异常！原因如下：", e);
        return Result.error(ResultCodeEnum.INTERNAL_SERVER_ERROR);
    }

}
