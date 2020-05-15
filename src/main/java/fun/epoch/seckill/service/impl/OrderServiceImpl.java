package fun.epoch.seckill.service.impl;

import fun.epoch.core.web.exception.BusinessException;
import fun.epoch.seckill.common.Constant;
import fun.epoch.seckill.dao.entity.Sequence;
import fun.epoch.seckill.dao.repository.OrderRepository;
import fun.epoch.seckill.dao.repository.SequenceRepository;
import fun.epoch.seckill.service.ItemService;
import fun.epoch.seckill.service.OrderService;
import fun.epoch.seckill.service.model.ItemModel;
import fun.epoch.seckill.service.model.OrderModel;
import fun.epoch.seckill.service.model.PromoModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static fun.epoch.core.web.response.DefaultResponseCode.*;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private SequenceRepository sequenceRepository;
    @Resource
    private OrderRepository orderRepository;
    @Resource
    private ItemService itemService;

    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer itemId, Integer amount, Integer promoId) {
        // 1. 校验购买数量
        if (amount > 1) {
            throw new BusinessException(ERROR, "购买数量不正确");
        }

        // 2. 生成订单入库
        OrderModel orderModel = insertOrder(userId, itemId, amount, promoId);

        // 3. 下单扣减库存
        if (!itemService.decreaseStock(itemId, amount)) {
            throw new BusinessException(INTERNAL_SERVER_ERROR, "扣减库存失败");
        }

        // 4. 下单新增销量
        if (!itemService.increaseSales(itemId, amount)) {
            throw new BusinessException(INTERNAL_SERVER_ERROR, "下单失败：无法新增商品销量");
        }

        // 5. 返回订单信息
        return orderModel;
    }

    private OrderModel insertOrder(Integer userId, Integer itemId, Integer amount, Integer promoId) {
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setItemId(itemId);
        orderModel.setAmount(amount);
        orderModel.setPromoId(promoId);
        // 根据是否拥有促销活动来设置订单商品价格
        ItemModel itemModel = itemService.detail(itemId);
        if (promoId == null) {
            orderModel.setItemPrice(itemModel.getPrice());
        } else {
            // 校验活动信息
            PromoModel promoModel = itemModel.getPromoModel();
            // 校验活动商品
            if (promoId.intValue() != promoModel.getId()) {
                throw new BusinessException(NOT_FOUND, "活动信息不正确");
            }
            // 校验活动状态
            if (promoModel.getStatus() != Constant.PromoStatus.STARTING) {
                throw new BusinessException(ERROR, "活动尚未开始");
            }
            orderModel.setItemPrice(promoModel.getPromoPrice());
        }
        orderModel.setOrderPrice(orderModel.getItemPrice().multiply(new BigDecimal(amount)));

        // 生成交易流水号：订单号
        orderModel.setOrderNo(generateOrderNo());
        try {
            orderRepository.save(orderModel.to());
        } catch (Exception e) {
            throw new BusinessException(INTERNAL_SERVER_ERROR, "订单保存失败", e);
        }
        return orderModel;
    }

    /**
     * 生成订单流水号
     * <p>
     * 流水号生成规则：
     * 共  16 位：2020040412345600
     * 前面 8 位：当前日期
     * 中间 6 位：自增序列
     * 后面 2 位：分库分表
     *
     * @return 订单流水号
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    String generateOrderNo() {
        return String.format("%s%s%s", getCurrentTime(), getDBSequence(), getDBLocation());
    }

    // 获取自增序列 -- 使用数据库中生成并获取
    private String getDBSequence() {
        // 获取订单序列号
        Sequence sequence = sequenceRepository.findByName("order_sequence")
                .orElseThrow(() -> new BusinessException(INTERNAL_SERVER_ERROR, "创建订单失败：生成订单流水号失败", "获取订单序列号失败"));

        // 更新序列号池的下个值
        int sequenceNumber = sequence.getValue();
        try {
            sequence.setValue(sequenceNumber + sequence.getStep());
            sequenceRepository.save(sequence);
        } catch (Exception e) {
            throw new BusinessException(INTERNAL_SERVER_ERROR, "创建订单失败：生成订单流水号失败", "更新订单序列号失败", e);
        }

        StringBuilder builder = new StringBuilder();

        // 位数不够则补零
        int length = String.valueOf(sequenceNumber).length();
        for (int i = 0; i < 6 - length; i++) {
            builder.append(0);
        }
        // 拼接序列字符串
        builder.append(sequenceNumber);

        return builder.toString();
    }

    // 获取分库分表 -- 暂时固定
    private String getDBLocation() {
        return "00";
    }

    // 获取当前日期 -- 年月日
    private String getCurrentTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_DATE).replace("-", "");
    }
}
