package asia.oxox.charon.simplecommerce.entity.DO;

import asia.oxox.charon.simplecommerce.enums.OrderStatusEnum;
import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName tb_order
 */
@TableName(value ="tb_order")
@Data
public class Order implements Serializable {
    /**
     * 主键-id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 订单状态
     */
    private OrderStatusEnum statusEnum;

    /**
     * 商品id
     */
    private Long goodsId;

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

    /**
     * 退款时间
     */
    private LocalDateTime refundTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}