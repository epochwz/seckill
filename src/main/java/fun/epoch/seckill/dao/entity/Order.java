package fun.epoch.seckill.dao.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "`order`")
public class Order extends BaseEntity {
    private String orderNo;
    private Integer userId;
    private Integer itemId;
    private Integer promoId;
    private Integer amount;
    private BigDecimal itemPrice;
    private BigDecimal orderPrice;
}
