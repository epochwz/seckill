package fun.epoch.seckill.dao.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
public class Promo extends BaseEntity {
    private Integer itemId;
    private String promoName;
    private BigDecimal promoPrice;
    private Date startTime;
    private Date endTime;
}
