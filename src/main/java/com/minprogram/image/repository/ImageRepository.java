package com.minprogram.image.repository;

import com.minprogram.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByTargetTypeAndTargetId(Integer targetType, Long targetId);
}