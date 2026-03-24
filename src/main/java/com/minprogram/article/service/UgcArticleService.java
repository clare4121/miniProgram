package com.minprogram.article.service;

import com.minprogram.article.dto.ArticleDetailResponse;
import com.minprogram.article.dto.CreateUgcArticleRequest;
import com.minprogram.article.entity.Article;
import com.minprogram.article.entity.ContentAudit;
import com.minprogram.article.repository.ArticleRepository;
import com.minprogram.article.repository.ContentAuditRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UgcArticleService {
    private final ArticleRepository articleRepository;
    private final ContentAuditRepository contentAuditRepository;
    private final ArticleService articleService;

    public UgcArticleService(ArticleRepository articleRepository,
                             ContentAuditRepository contentAuditRepository,
                             ArticleService articleService) {
        this.articleRepository = articleRepository;
        this.contentAuditRepository = contentAuditRepository;
        this.articleService = articleService;
    }

    @Transactional
    public ArticleDetailResponse create(Long userId, CreateUgcArticleRequest req) {
        Article article = new Article();
        article.setAuthorId(userId);
        article.setTitle(req.getTitle());
        article.setSummary(req.getSummary());
        article.setContent(req.getContent());
        article.setCoverUrl(req.getCoverUrl());
        article.setDestinationId(req.getDestinationId());
        article.setSourceType(2);
        article.setAuditStatus(0);
        article.setStatus(1);
        article = articleRepository.save(article);

        ContentAudit audit = new ContentAudit();
        audit.setTargetType(1);
        audit.setTargetId(article.getId());
        audit.setSubmitUserId(userId);
        audit.setStatus(0);
        contentAuditRepository.save(audit);
        return articleService.toDetail(article);
    }

    @Transactional
    public ArticleDetailResponse update(Long userId, Long id, CreateUgcArticleRequest req) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("攻略不存在"));
        if (!article.getAuthorId().equals(userId) || !Integer.valueOf(2).equals(article.getSourceType())) {
            throw new IllegalArgumentException("无权编辑该UGC攻略");
        }
        article.setTitle(req.getTitle());
        article.setSummary(req.getSummary());
        article.setContent(req.getContent());
        article.setCoverUrl(req.getCoverUrl());
        article.setDestinationId(req.getDestinationId());
        article.setAuditStatus(0);
        article = articleRepository.save(article);

        ContentAudit audit = contentAuditRepository.findByTargetTypeAndTargetId(1, id).orElseGet(ContentAudit::new);
        audit.setTargetType(1);
        audit.setTargetId(id);
        audit.setSubmitUserId(userId);
        audit.setStatus(0);
        audit.setReason(null);
        audit.setReviewerId(null);
        audit.setReviewedAt(null);
        contentAuditRepository.save(audit);
        return articleService.toDetail(article);
    }
}
