package com.minprogram.article.repository;

import com.minprogram.article.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByStatusAndAuditStatusOrderByIdDesc(Integer status, Integer auditStatus);

    List<Article> findByStatusAndAuditStatusAndDestinationIdOrderByIdDesc(Integer status, Integer auditStatus, Long destinationId);

    List<Article> findByStatusAndSourceTypeAndAuditStatusOrderByIdDesc(Integer status, Integer sourceType, Integer auditStatus);

    List<Article> findByStatusAndSourceTypeAndAuditStatusAndAuthorIdOrderByIdDesc(Integer status, Integer sourceType, Integer auditStatus, Long authorId);

    Page<Article> findByStatusAndAuditStatusAndTitleContainingOrderByIdDesc(Integer status, Integer auditStatus, String keyword, Pageable pageable);

    Page<Article> findByStatusAndAuditStatusAndDestinationIdAndTitleContainingOrderByIdDesc(Integer status, Integer auditStatus, Long destinationId, String keyword, Pageable pageable);

    Page<Article> findByStatusAndAuditStatusOrderByIdDesc(Integer status, Integer auditStatus, Pageable pageable);

    Page<Article> findByStatusAndAuditStatusAndDestinationIdOrderByIdDesc(Integer status, Integer auditStatus, Long destinationId, Pageable pageable);
}
