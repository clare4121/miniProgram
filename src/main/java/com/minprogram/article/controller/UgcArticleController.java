package com.minprogram.article.controller;

import com.minprogram.article.dto.ArticleDetailResponse;
import com.minprogram.article.dto.CreateUgcArticleRequest;
import com.minprogram.article.service.UgcArticleService;
import com.minprogram.common.api.ApiResponse;
import com.minprogram.common.security.UserContext;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ugc/articles")
public class UgcArticleController {
    private final UgcArticleService ugcArticleService;

    public UgcArticleController(UgcArticleService ugcArticleService) {
        this.ugcArticleService = ugcArticleService;
    }

    @PostMapping
    public ApiResponse<ArticleDetailResponse> create(@Valid @RequestBody CreateUgcArticleRequest req) {
        Long userId = UserContext.getUserId();
        if (userId == null) {
            return ApiResponse.fail("未登录");
        }
        return ApiResponse.ok(ugcArticleService.create(userId, req));
    }

    @PutMapping("/{id}")
    public ApiResponse<ArticleDetailResponse> update(@PathVariable("id") Long id,
                                                     @Valid @RequestBody CreateUgcArticleRequest req) {
        Long userId = UserContext.getUserId();
        if (userId == null) {
            return ApiResponse.fail("未登录");
        }
        return ApiResponse.ok(ugcArticleService.update(userId, id, req));
    }
}
