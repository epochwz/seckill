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
public class UserPassword extends BaseEntity {
    private Integer userId;
    private String password;
}
