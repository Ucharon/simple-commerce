package asia.oxox.charon.simplecommerce.entity.DO;

import asia.oxox.charon.simplecommerce.enums.OrderStatusEnum;
import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName tb_balance_detail
 */
@TableName(value ="tb_balance_detail")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BalanceDetail implements Serializable {
    /**
     * id
     */
    @TableId
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 原余额
     */
    private BigDecimal originalBalance;

    /**
     * 后余额
     */
    private BigDecimal finalBalance;

    /**
     * 变动原因状态
     */
    private OrderStatusEnum statusEnum;

    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    private Integer delFlag;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}