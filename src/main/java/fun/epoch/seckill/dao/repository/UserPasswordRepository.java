package fun.epoch.seckill.dao.repository;

import fun.epoch.seckill.dao.entity.UserPassword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPasswordRepository extends JpaRepository<UserPassword, Integer> {
    Optional<UserPassword> findByUserId(Integer userId);
}
