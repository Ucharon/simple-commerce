package asia.oxox.charon.simplecommerce.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;


@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum OrderStatusEnum {

    COMPLETED(1, "已完成"),
    REFUNDED(2, "已退款");

    @EnumValue
    private final Integer statusCode;
    private final String statusName;

    @JsonCreator
    public static OrderStatusEnum getByCode(Integer code) {
        return Arrays.stream(OrderStatusEnum.values())
                .filter(orderStatusEnum -> orderStatusEnum.getStatusCode().equals(code))
                .findFirst()
                .orElse(null);
    }
}
