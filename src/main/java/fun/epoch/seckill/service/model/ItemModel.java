package fun.epoch.seckill.service.model;

import fun.epoch.core.web.bean.BeanConverter;
import fun.epoch.seckill.dao.entity.Item;
import fun.epoch.seckill.dao.entity.ItemStock;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class ItemModel {
    // 商品主键
    private Integer id;
    // 商品名称
    @NotBlank(message = "商品名称不能为空")
    private String title;
    // 商品描述
    @NotBlank(message = "商品描述不能为空")
    private String description;
    // 商品图片
    @NotBlank(message = "图片链接不能为空")
    private String imageUrl;
    // 商品库存
    @NotNull
    @Range(message = "商品库存不能小于 0")
    private Integer stock;
    // 商品价格
    @NotNull
    @Range(message = "商品价格不能小于 0")
    private BigDecimal price;
    @Range(message = "商品销量不能小于 0")
    private Integer sales;
    @Valid // 非空时表示该商品正在进行促销活动
    private PromoModel promoModel;

    public static ItemModel of(@NonNull Item item, @NonNull ItemStock itemStock) {
        ItemModel itemModel = BeanConverter.convert(item, new ItemModel());
        itemModel.setStock(itemStock.getStock());
        return itemModel;
    }

    public Item to() {
        return BeanConverter.convert(this, new Item());
    }

    public ItemStock toItemStock() {
        ItemStock itemStock = BeanConverter.convert(this, new ItemStock());
        itemStock.setItemId(this.getId());
        return itemStock;
    }
}