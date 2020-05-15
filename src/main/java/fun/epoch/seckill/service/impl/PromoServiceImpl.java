package fun.epoch.seckill.service.impl;

import fun.epoch.core.web.exception.BusinessException;
import fun.epoch.seckill.dao.repository.PromoRepository;
import fun.epoch.seckill.service.PromoService;
import fun.epoch.seckill.service.model.PromoModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static fun.epoch.core.web.response.DefaultResponseCode.NOT_FOUND;
import static fun.epoch.seckill.common.Constant.PromoStatus.*;

@Service
public class PromoServiceImpl implements PromoService {
    @Resource
    private PromoRepository promoRepository;

    @Override
    public PromoModel findPromoByItemId(Integer itemId) {
        PromoModel promoModel = promoRepository.findByItemId(itemId).map(PromoModel::of)
                .orElseThrow(() -> new BusinessException(NOT_FOUND, String.format("查找不到商品 [%s] 的促销活动信息", itemId)));

        // 设置促销活动状态
        long startTime = promoModel.getStartTime().getTime();
        long endTime = promoModel.getEndTime().getTime();
        long now = System.currentTimeMillis();

        if (now < startTime) {
            promoModel.setStatus(UN_START);
        } else if (now >= endTime) {
            promoModel.setStatus(DISABLED);
        } else {
            promoModel.setStatus(STARTING);
        }
        return promoModel;
    }
}
