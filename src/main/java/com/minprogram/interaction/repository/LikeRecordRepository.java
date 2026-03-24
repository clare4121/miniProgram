package com.minprogram.interaction.repository;

import com.minprogram.interaction.entity.LikeRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRecordRepository extends JpaRepository<LikeRecord, Long> {
    Optional<LikeRecord> findByUserIdAndTargetTypeAndTargetId(Long userId, Integer targetType, Long targetId);

    long countByTargetTypeAndTargetId(Integer targetType, Long targetId);
}
