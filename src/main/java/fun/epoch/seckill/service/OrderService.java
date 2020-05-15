package fun.epoch.seckill.service;

import fun.epoch.seckill.service.model.OrderModel;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Validated
public interface OrderService {
    /**
     * 创建订单
     *
     * @param userId  用户主键
     * @param itemId  商品主键
     * @param amount  商品数量
     * @param promoId 促销活动 (非空时表示拥有促销活动)
     * @return 订单信息
     */
    OrderModel createOrder(
            @NotNull(message = "创建订单失败：用户 ID 不能为空") Integer userId,
            @NotNull(message = "创建订单失败：商品 ID 不能为空") Integer itemId,
            @NotNull @Min(value = 1, message = "创建订单失败：购买的商品数量必须大于 0") Integer amount,
            Integer promoId
    );
}
