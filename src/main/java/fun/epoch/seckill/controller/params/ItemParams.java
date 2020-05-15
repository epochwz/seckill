package fun.epoch.seckill.controller.params;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class ItemParams {
    @NotBlank(message = "商品名称不能为空")
    private String title;
    @NotBlank(message = "商品描述不能为空")
    private String description;
    @NotBlank(message = "商品图片不能为空")
    private String imageUrl;
    @NotNull
    @Range(message = "商品价格不能小于 0")
    private BigDecimal price;
    @NotNull
    @Range(message = "商品库存不能小于 0")
    private Integer stock;
}
