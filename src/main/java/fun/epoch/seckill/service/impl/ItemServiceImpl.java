package fun.epoch.seckill.service.impl;

import fun.epoch.core.web.exception.BusinessException;
import fun.epoch.seckill.common.Constant;
import fun.epoch.seckill.dao.entity.Item;
import fun.epoch.seckill.dao.entity.ItemStock;
import fun.epoch.seckill.dao.repository.ItemRepository;
import fun.epoch.seckill.dao.repository.ItemStockRepository;
import fun.epoch.seckill.service.ItemService;
import fun.epoch.seckill.service.PromoService;
import fun.epoch.seckill.service.model.ItemModel;
import fun.epoch.seckill.service.model.PromoModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static fun.epoch.core.web.response.DefaultResponseCode.INTERNAL_SERVER_ERROR;
import static fun.epoch.core.web.response.DefaultResponseCode.NOT_FOUND;

@Service
public class ItemServiceImpl implements ItemService {
    @Resource
    private ItemRepository itemRepository;

    @Resource
    private ItemStockRepository itemStockRepository;

    @Resource
    private PromoService promoService;

    @Override
    public ItemModel detail(Integer id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new BusinessException(NOT_FOUND, "获取商品详情失败：查找不到商品信息"));

        ItemStock itemStock = itemStockRepository.findById(item.getId())
                .orElseThrow(() -> new BusinessException(NOT_FOUND, "获取商品详情失败：查找不到商品库存"));

        ItemModel itemModel = ItemModel.of(item, itemStock);

        try {
            PromoModel promoModel = promoService.findPromoByItemId(itemModel.getId());
            if (promoModel.getStatus() == Constant.PromoStatus.UN_START ||
                    promoModel.getStatus() == Constant.PromoStatus.STARTING
            ) {
                itemModel.setPromoModel(promoModel);
            }
            return itemModel;
        } catch (BusinessException e) {
            return itemModel;
        }
    }

    @Override
    public List<ItemModel> list() {
        return itemRepository.findAll().stream().map(item ->
                ItemModel.of(item, itemStockRepository.findById(item.getId()).orElse(new ItemStock()))
        ).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ItemModel createItem(ItemModel itemModel) {
        // 商品信息入库
        try {
            Item item = itemModel.to();
            itemRepository.save(item);
            itemModel.setId(item.getId());
        } catch (Exception e) {
            throw new BusinessException(INTERNAL_SERVER_ERROR, "添加商品失败：插入商品记录失败", e);
        }

        // 商品库存入库
        try {
            itemStockRepository.save(itemModel.toItemStock());
        } catch (Exception e) {
            throw new BusinessException(INTERNAL_SERVER_ERROR, "添加商品失败：插入商品库存失败", e);
        }

        // 返回新增商品
        return this.detail(itemModel.getId());
    }

    @Override
    @Transactional
    public boolean decreaseStock(Integer itemId, Integer amount) {
        return itemStockRepository.decreaseStock(itemId, amount) == 1;
    }

    @Override
    @Transactional
    public boolean increaseSales(Integer itemId, Integer amount) {
        return itemRepository.increaseSales(itemId, amount) == 1;
    }
}
