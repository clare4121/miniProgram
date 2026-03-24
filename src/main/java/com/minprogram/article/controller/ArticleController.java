package com.minprogram.article.controller;

import com.minprogram.article.dto.ArticleDetailResponse;
import com.minprogram.article.dto.ArticleListItemResponse;
import com.minprogram.article.service.ArticleService;
import com.minprogram.common.api.ApiResponse;
import com.minprogram.common.api.PageResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public ApiResponse<List<ArticleListItemResponse>> list(@RequestParam(required = false) Long destinationId) {
        return ApiResponse.ok(articleService.list(destinationId));
    }

    @GetMapping("/page")
    public ApiResponse<PageResponse<ArticleListItemResponse>> page(
            @RequestParam(required = false) Long destinationId,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        return ApiResponse.ok(articleService.page(destinationId, keyword, page, size));
    }

    @GetMapping("/{id}")
    public ApiResponse<ArticleDetailResponse> detail(@PathVariable("id") Long id) {
        return ApiResponse.ok(articleService.detail(id));
    }
}
