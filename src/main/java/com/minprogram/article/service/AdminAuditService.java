package com.minprogram.article.service;

import com.minprogram.article.entity.Article;
import com.minprogram.article.entity.ContentAudit;
import com.minprogram.article.repository.ArticleRepository;
import com.minprogram.article.repository.ContentAuditRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminAuditService {
    private final ContentAuditRepository contentAuditRepository;
    private final ArticleRepository articleRepository;

    public AdminAuditService(ContentAuditRepository contentAuditRepository, ArticleRepository articleRepository) {
        this.contentAuditRepository = contentAuditRepository;
        this.articleRepository = articleRepository;
    }

    public List<ContentAudit> listPending() {
        return contentAuditRepository.findByStatusOrderByIdDesc(0);
    }

    @Transactional
    public void approve(Long auditId, Long reviewerId) {
        ContentAudit audit = contentAuditRepository.findById(auditId).orElseThrow(() -> new IllegalArgumentException("审核记录不存在"));
        Article article = articleRepository.findById(audit.getTargetId()).orElseThrow(() -> new IllegalArgumentException("攻略不存在"));
        audit.setStatus(1);
        audit.setReason(null);
        audit.setReviewerId(reviewerId);
        audit.setReviewedAt(LocalDateTime.now());
        contentAuditRepository.save(audit);
        article.setAuditStatus(1);
        articleRepository.save(article);
    }

    @Transactional
    public void reject(Long auditId, Long reviewerId, String reason) {
        ContentAudit audit = contentAuditRepository.findById(auditId).orElseThrow(() -> new IllegalArgumentException("审核记录不存在"));
        Article article = articleRepository.findById(audit.getTargetId()).orElseThrow(() -> new IllegalArgumentException("攻略不存在"));
        audit.setStatus(2);
        audit.setReason(reason);
        audit.setReviewerId(reviewerId);
        audit.setReviewedAt(LocalDateTime.now());
        contentAuditRepository.save(audit);
        article.setAuditStatus(2);
        articleRepository.save(article);
    }
}
