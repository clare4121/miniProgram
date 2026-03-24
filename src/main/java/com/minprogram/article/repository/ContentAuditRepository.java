package com.minprogram.article.repository;

import com.minprogram.article.entity.ContentAudit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContentAuditRepository extends JpaRepository<ContentAudit, Long> {
    Optional<ContentAudit> findByTargetTypeAndTargetId(Integer targetType, Long targetId);

    List<ContentAudit> findByStatusOrderByIdDesc(Integer status);
}
