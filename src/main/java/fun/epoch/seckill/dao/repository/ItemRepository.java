package fun.epoch.seckill.dao.repository;

import fun.epoch.seckill.dao.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE item i SET i.sales=i.sales+:amount WHERE i.id=:itemId")
    int increaseSales(@Param("itemId") Integer itemId, @Param("amount") Integer amount);
}