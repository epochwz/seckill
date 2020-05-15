package fun.epoch.seckill.service;

import fun.epoch.seckill.service.model.ItemModel;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface ItemService {
    /**
     * 创建商品
     *
     * @param itemModel 商品信息
     */
    ItemModel createItem(@Valid ItemModel itemModel);

    /**
     * 查询商品详情
     *
     * @param id 商品主键
     */
    ItemModel detail(@NotNull(message = "查询商品详情失败：商品 ID 不能为空") Integer id);

    /**
     * 查询商品列表
     */
    List<ItemModel> list();

    /**
     * 扣减库存
     *
     * @param itemId 商品主键
     * @param amount 商品数量
     */
    boolean decreaseStock(
            @NotNull(message = "扣减商品库存失败：商品 ID 不能为空") Integer itemId,
            @NotNull @Min(value = 1, message = "扣减商品库存失败，扣减数量必须大于 0") Integer amount
    );

    /**
     * 增加销量
     *
     * @param itemId 商品主键
     * @param amount 商品数量
     */
    boolean increaseSales(
            @NotNull(message = "增加商品销量失败：商品 ID 不能为空") Integer itemId,
            @NotNull @Min(value = 1, message = "增加商品销量失败，增加数量必须大于 0") Integer amount
    );
}
