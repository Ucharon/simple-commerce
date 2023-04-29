package asia.oxox.charon.simplecommerce.entity.DO;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName tb_goods
 */
@TableName(value ="tb_goods")
@Data
public class Goods implements Serializable {
    /**
     * 商品id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 详细信息
     */
    private String details;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 库存
     */
    private Long stock;

    /**
     * 缩略图地址
     */
    private String thumbnail;

    /**
     * 商品状态字段（0表示未下架，1表示已下架）
     */
    private String status;

    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    private Integer delFlag;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}