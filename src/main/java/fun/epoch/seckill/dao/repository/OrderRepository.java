package fun.epoch.seckill.dao.repository;

import fun.epoch.seckill.dao.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
