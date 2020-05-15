package fun.epoch.seckill.service;

import fun.epoch.seckill.service.model.PromoModel;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public interface PromoService {
    /**
     * 查找促销活动
     *
     * @param itemId 商品主键
     * @return 促销活动信息
     */
    PromoModel findPromoByItemId(@NotNull(message = "获取促销活动失败：商品 ID 不能为空") Integer itemId);
}
