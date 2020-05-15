package fun.epoch.seckill.controller.vo;

import fun.epoch.core.web.bean.BeanConverter;
import fun.epoch.seckill.service.model.ItemModel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class ItemVO {
    // 商品 ID
    private Integer id;
    // 商品名称
    private String title;
    // 商品描述
    private String description;
    // 商品图片
    private String imageUrl;
    // 商品价格
    private BigDecimal price;
    // 商品库存
    private Integer stock;
    // 商品销量
    private Integer sales;
    // 秒杀活动状态：0 表示没有秒杀活动，1 表示秒杀活动未开始，2 表示秒杀活动进行中，3 表示秒杀活动已结束
    private Integer promoStatus = 0;
    // 秒杀活动 ID
    private Integer promoId;
    // 秒杀活动价格
    private BigDecimal promoPrice;
    // 秒杀活动开始时间
    private Long startTime;
    // 秒杀活动结束时间
    private Long endTime;

    public static ItemVO of(@NonNull ItemModel itemModel) {
        ItemVO itemVO = BeanConverter.convert(itemModel, new ItemVO());
        Optional.ofNullable(itemModel.getPromoModel()).ifPresent(
                promoModel -> {
                    itemVO.setPromoId(promoModel.getId());
                    itemVO.setPromoStatus(promoModel.getStatus());
                    itemVO.setPromoPrice(promoModel.getPromoPrice());
                    itemVO.setStartTime(promoModel.getStartTime().getTime());
                    itemVO.setEndTime(promoModel.getEndTime().getTime());
                }
        );
        return itemVO;
    }

    public static List<ItemVO> of(List<ItemModel> list) {
        return Optional.ofNullable(list).map(List -> list.stream().map(ItemVO::of).collect(Collectors.toList())).orElse(new ArrayList<>());
    }
}