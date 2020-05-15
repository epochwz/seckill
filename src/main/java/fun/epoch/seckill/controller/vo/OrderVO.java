package fun.epoch.seckill.controller.vo;

import fun.epoch.core.web.bean.BeanConverter;
import fun.epoch.seckill.service.model.OrderModel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class OrderVO {
    // 订单唯一流水号
    private String orderNo;
    // 订单的用户 ID
    private Integer userId;
    // 购买商品的 ID
    private Integer itemId;
    // 订单的活动 ID
    private Integer promoId;
    // 购买商品的数量
    private Integer amount;
    // 购买商品的单价
    private BigDecimal itemPrice;
    // 购买商品的总价
    private BigDecimal orderPrice;

    public static OrderVO of(@NonNull OrderModel orderModel) {
        return BeanConverter.convert(orderModel, new OrderVO());
    }
}