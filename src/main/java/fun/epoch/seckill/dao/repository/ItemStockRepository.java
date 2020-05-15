package fun.epoch.seckill.dao.repository;

import fun.epoch.seckill.dao.entity.ItemStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemStockRepository extends JpaRepository<ItemStock, Integer> {
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE item_stock t SET t.stock=t.stock-:amount WHERE t.item_id=:itemId AND t.stock>=:amount")
    int decreaseStock(@Param("itemId") Integer itemId, @Param("amount") Integer amount);
}
