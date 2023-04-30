package asia.oxox.charon.simplecommerce.entity.VO;

import asia.oxox.charon.simplecommerce.enums.ResultCodeEnum;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import xin.altitude.cms.common.util.FieldFilterUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static asia.oxox.charon.simplecommerce.enums.ResultCodeEnum.SUCCESS;


/**
 * @program: simple-frame
 * @description:
 * @author: Charon
 * @create: 2023-04-10 14:32
 **/
@Data
@NoArgsConstructor
@ApiModel("通用返回数据结构")
public class Result {

    @ApiModelProperty("状态码")
    private Integer code;
    @ApiModelProperty("描述信息")
    private String desc;
    @ApiModelProperty(value = "返回信息", allowEmptyValue = true)
    private Object data;
    @ApiModelProperty(value = "额外信息", allowEmptyValue = true)
    private Map<String, Object> map = new HashMap<>();
    @ApiModelProperty("是否成功")
    private Boolean success;

    public Result(Integer code, String desc, Boolean success) {
        this.code = code;
        this.desc = desc;
        this.success = success;
    }

    public Result(Integer code, String desc, Object data, Boolean success) {
        this.code = code;
        this.desc = desc;
        this.data = data;
        this.success = success;
    }

    public static Result success() {
        return new Result(SUCCESS.getCode(), SUCCESS.getDesc(), true);
    }

    public static Result success(Object data) {
        return new Result(SUCCESS.getCode(), SUCCESS.getDesc(), data, true);
    }

    /**
     * 完成对象实体类的属性过滤
     *
     * @param data   原始对象实例
     * @param action 方法引用选中需要过滤排除的列
     * @param <T>    原始数据类型
     * @return Result
     */
    @SafeVarargs
    public static <T> Result success(T data, final SFunction<T, ? extends Serializable>... action) {
        return success(data, false, action);
    }

    /**
     * 完成对象实体类的属性过滤
     *
     * @param data      原始对象实例
     * @param isInclude 如果是true代表保留字段、false代表排除字段
     * @param action    方法引用选中需要过滤排除的列
     * @param <T>       原始数据类型
     * @return Result
     */
    @SafeVarargs
    public static <T> Result success(T data, boolean isInclude, final SFunction<T, ? extends Serializable>... action) {
        return success(FieldFilterUtils.filterFields(data, isInclude, action));
    }

    /**
     * 完成列表对象实体类的属性过滤
     *
     * @param data   原始列表对象实例
     * @param action 方法引用选中需要过滤排除的列
     * @param <T>    原始数据类型
     * @return Result
     */
    @SafeVarargs
    public static <T> Result success(List<T> data, final SFunction<T, ? extends Serializable>... action) {
        return success(data, false, action);
    }

    /**
     * 完成列表对象实体类的属性过滤
     *
     * @param data      原始列表对象实例
     * @param isInclude 如果是true代表保留字段、false代表排除字段
     * @param action    方法引用选中需要过滤排除的列
     * @param <T>       原始数据类型
     * @return Result
     */
    @SafeVarargs
    public static <T> Result success(List<T> data, boolean isInclude, final SFunction<T, ? extends Serializable>... action) {
        return success(FieldFilterUtils.filterFields(data, isInclude, action));
    }

    /**
     * 完成分页对象实体类的属性过滤
     *
     * @param data   原始分页对象实例
     * @param action 方法引用选中需要过滤排除的列
     * @param <T>    原始数据类型
     * @return Result
     */
    @SafeVarargs
    public static <T> Result success(IPage<T> data, final SFunction<T, ? extends Serializable>... action) {
        return success(data, false, action);
    }

    /**
     * 完成分页对象实体类的属性过滤
     *
     * @param data      原始分页对象实例
     * @param isInclude 如果是true代表保留字段、false代表排除字段
     * @param action    方法引用选中需要过滤排除的列
     * @param <T>       原始数据类型
     * @return Result
     */
    @SafeVarargs
    public static <T> Result success(IPage<T> data, boolean isInclude, final SFunction<T, ? extends Serializable>... action) {
        return success(FieldFilterUtils.filterFields(data, isInclude, action));
    }

    public Result put(String key, Object value) {
        map.put(key, value);
        return this;
    }

    /**
     * 返回错误信息
     *
     * @param resultCodeEnum
     * @return
     */
    public static Result error(ResultCodeEnum resultCodeEnum) {
        return new Result(resultCodeEnum.getCode(), resultCodeEnum.getDesc(), false);
    }

    public static Result error(ResultCodeEnum resultCodeEnum, String desc) {
        return new Result(resultCodeEnum.getCode(), desc, false);
    }

}
