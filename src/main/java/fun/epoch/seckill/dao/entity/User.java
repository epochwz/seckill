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
public class User extends BaseEntity {
    private String username;
    private String telphone;
    private Integer age;
    private Byte gender;
    private String registerMode;
    private String thirdPartyId;
}
