package com.minprogram.article.service;

import com.minprogram.article.dto.ArticleDetailResponse;
import com.minprogram.article.dto.ArticleListItemResponse;
import com.minprogram.article.entity.Article;
import com.minprogram.article.repository.ArticleRepository;
import com.minprogram.common.api.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<ArticleListItemResponse> list(Long destinationId) {
        List<Article> list = destinationId == null
                ? articleRepository.findByStatusAndAuditStatusOrderByIdDesc(1, 1)
                : articleRepository.findByStatusAndAuditStatusAndDestinationIdOrderByIdDesc(1, 1, destinationId);
        List<ArticleListItemResponse> out = new ArrayList<>();
        for (Article a : list) {
            ArticleListItemResponse item = new ArticleListItemResponse();
            item.setId(a.getId());
            item.setTitle(a.getTitle());
            item.setSummary(a.getSummary());
            item.setCoverUrl(a.getCoverUrl());
            item.setDestinationId(a.getDestinationId());
            item.setLikeCount(a.getLikeCount());
            item.setFavoriteCount(a.getFavoriteCount());
            item.setCommentCount(a.getCommentCount());
            out.add(item);
        }
        return out;
    }

    public PageResponse<ArticleListItemResponse> page(Long destinationId, String keyword, Integer page, Integer size) {
        int currentPage = page == null || page < 1 ? 1 : page;
        int currentSize = size == null || size < 1 ? 10 : Math.min(size, 50);
        PageRequest pageable = PageRequest.of(currentPage - 1, currentSize);
        String kw = StringUtils.hasText(keyword) ? keyword.trim() : null;

        Page<Article> result;
        if (destinationId != null && StringUtils.hasText(kw)) {
            result = articleRepository.findByStatusAndAuditStatusAndDestinationIdAndTitleContainingOrderByIdDesc(1, 1, destinationId, kw, pageable);
        } else if (destinationId != null) {
            result = articleRepository.findByStatusAndAuditStatusAndDestinationIdOrderByIdDesc(1, 1, destinationId, pageable);
        } else if (StringUtils.hasText(kw)) {
            result = articleRepository.findByStatusAndAuditStatusAndTitleContainingOrderByIdDesc(1, 1, kw, pageable);
        } else {
            result = articleRepository.findByStatusAndAuditStatusOrderByIdDesc(1, 1, pageable);
        }

        PageResponse<ArticleListItemResponse> resp = new PageResponse<>();
        resp.setPage(currentPage);
        resp.setSize(currentSize);
        resp.setTotal(result.getTotalElements());
        resp.setRecords(result.getContent().stream().map(this::toListItem).collect(java.util.stream.Collectors.toList()));
        return resp;
    }

    public ArticleDetailResponse detail(Long id) {
        Article a = articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("攻略不存在"));
        if (!Integer.valueOf(1).equals(a.getStatus()) || !Integer.valueOf(1).equals(a.getAuditStatus())) {
            throw new IllegalArgumentException("攻略不可见");
        }
        a.setViewCount(a.getViewCount() == null ? 1 : a.getViewCount() + 1);
        articleRepository.save(a);
        return toDetail(a);
    }

    public ArticleDetailResponse toDetail(Article a) {
        ArticleDetailResponse resp = new ArticleDetailResponse();
        resp.setId(a.getId());
        resp.setAuthorId(a.getAuthorId());
        resp.setTitle(a.getTitle());
        resp.setSummary(a.getSummary());
        resp.setContent(a.getContent());
        resp.setCoverUrl(a.getCoverUrl());
        resp.setDestinationId(a.getDestinationId());
        resp.setSourceType(a.getSourceType());
        resp.setAuditStatus(a.getAuditStatus());
        resp.setViewCount(a.getViewCount());
        resp.setLikeCount(a.getLikeCount());
        resp.setFavoriteCount(a.getFavoriteCount());
        resp.setCommentCount(a.getCommentCount());
        return resp;
    }

    public ArticleListItemResponse toListItem(Article a) {
        ArticleListItemResponse item = new ArticleListItemResponse();
        item.setId(a.getId());
        item.setTitle(a.getTitle());
        item.setSummary(a.getSummary());
        item.setCoverUrl(a.getCoverUrl());
        item.setDestinationId(a.getDestinationId());
        item.setLikeCount(a.getLikeCount());
        item.setFavoriteCount(a.getFavoriteCount());
        item.setCommentCount(a.getCommentCount());
        return item;
    }
}
