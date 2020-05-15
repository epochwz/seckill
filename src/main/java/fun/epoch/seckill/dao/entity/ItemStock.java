package fun.epoch.seckill.dao.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "item_stock")
public class ItemStock extends BaseEntity {
    private Integer itemId;
    private Integer stock;
}
