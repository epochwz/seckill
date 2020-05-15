package fun.epoch.seckill.dao.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
public class Item extends BaseEntity {
    private String title;
    private String description;
    private String imageUrl;
    private BigDecimal price;
    private Integer sales;
}
