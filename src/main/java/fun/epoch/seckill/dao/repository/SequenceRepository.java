package fun.epoch.seckill.dao.repository;

import fun.epoch.seckill.dao.entity.Sequence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SequenceRepository extends JpaRepository<Sequence, Integer> {
    Optional<Sequence> findByName(String name);
}
