package fun.epoch.seckill.service.model;

import fun.epoch.core.web.bean.BeanConverter;
import fun.epoch.seckill.dao.entity.Promo;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
public class PromoModel {
    // 活动主键
    private Integer id;
    // 商品主键
    @NotNull(message = "活动的商品主键不能为空")
    private Integer itemId;
    // 活动名称
    @NotBlank(message = "促销活动的名称不能为空")
    private String promoName;
    // 活动的商品价格
    @NotNull
    @Range(message = "活动的商品价格不能小于 0")
    private BigDecimal promoPrice;
    // 活动的开始时间
    @NotNull(message = "活动的开始时间不能为空")
    private Date startTime;
    // 活动的结束时间
    @NotNull(message = "活动的结束时间不能为空")
    private Date endTime;
    // 活动状态：1 表示未开始，2 表示进行中，3 表示已结束
    private Integer status;

    public static PromoModel of(@NonNull Promo promo) {
        return BeanConverter.convert(promo, new PromoModel());
    }
}
