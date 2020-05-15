package fun.epoch.seckill.dao.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Description: Common Entity
 * <p>
 * Created by epoch
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Integer id;
    @CreatedDate
    @Column(insertable = false, updatable = false)
    private Timestamp createTime;
    @LastModifiedDate
    @Column(insertable = false, updatable = false)
    private Timestamp updateTime;
}
