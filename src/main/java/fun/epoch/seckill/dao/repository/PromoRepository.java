package fun.epoch.seckill.dao.repository;

import fun.epoch.seckill.dao.entity.Promo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PromoRepository extends JpaRepository<Promo, Integer> {
    Optional<Promo> findByItemId(Integer itemId);
}
