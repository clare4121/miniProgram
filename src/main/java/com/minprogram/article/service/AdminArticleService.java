package com.minprogram.article.service;

import com.minprogram.article.dto.ArticleDetailResponse;
import com.minprogram.article.dto.CreateOfficialArticleRequest;
import com.minprogram.article.entity.Article;
import com.minprogram.article.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleService articleService;

    public AdminArticleService(ArticleRepository articleRepository, ArticleService articleService) {
        this.articleRepository = articleRepository;
        this.articleService = articleService;
    }

    @Transactional
    public ArticleDetailResponse createOfficial(Long adminUserId, CreateOfficialArticleRequest req) {
        Article article = new Article();
        article.setAuthorId(adminUserId);
        article.setTitle(req.getTitle());
        article.setSummary(req.getSummary());
        article.setContent(req.getContent());
        article.setCoverUrl(req.getCoverUrl());
        article.setDestinationId(req.getDestinationId());
        article.setSourceType(1);
        article.setAuditStatus(1);
        article.setStatus(req.getStatus() == null ? 1 : req.getStatus());
        article = articleRepository.save(article);
        return articleService.toDetail(article);
    }
}
