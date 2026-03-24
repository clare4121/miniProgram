package com.minprogram.interaction.repository;

import com.minprogram.interaction.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Optional<Favorite> findByUserIdAndTargetTypeAndTargetId(Long userId, Integer targetType, Long targetId);

    long countByTargetTypeAndTargetId(Integer targetType, Long targetId);
}
