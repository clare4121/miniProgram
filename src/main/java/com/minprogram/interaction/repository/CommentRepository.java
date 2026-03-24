package com.minprogram.interaction.repository;

import com.minprogram.interaction.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByTargetTypeAndTargetIdAndStatusOrderByIdDesc(Integer targetType, Long targetId, Integer status);

    long countByTargetTypeAndTargetIdAndStatus(Integer targetType, Long targetId, Integer status);
}
