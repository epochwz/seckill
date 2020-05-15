package fun.epoch.seckill.service.model;

import fun.epoch.core.web.bean.BeanConverter;
import fun.epoch.seckill.dao.entity.Order;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class OrderModel {
    // 订单唯一流水号
    @NotBlank(message = "订单流水号不能为空")
    private String orderNo;
    // 订单的用户 ID
    @NotNull(message = "订单的用户 ID 不能为空")
    private Integer userId;
    // 购买商品的 ID
    @NotNull(message = "订单的商品 ID 不能为空")
    private Integer itemId;
    // 订单的活动 ID
    private Integer promoId;
    // 购买商品的数量
    @NotNull
    @Range(message = "订单购买商品的数量不能小于 0")
    private Integer amount;
    // 购买商品的单价
    @NotNull
    @Range(message = "订单购买商品的单价不能小于 0")
    private BigDecimal itemPrice;
    // 购买商品的总价
    @NotNull
    @Range(message = "订单购买商品的总价不能小于 0")
    private BigDecimal orderPrice;

    public Order to() {
        return BeanConverter.convert(this, new Order());
    }
}