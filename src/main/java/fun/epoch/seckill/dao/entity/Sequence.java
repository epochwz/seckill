package fun.epoch.seckill.dao.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
public class Sequence extends BaseEntity {
    private String name;
    private Integer value;
    private Integer step;
}
