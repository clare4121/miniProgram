package com.minprogram.article.controller;

import com.minprogram.article.dto.ArticleDetailResponse;
import com.minprogram.article.dto.CreateOfficialArticleRequest;
import com.minprogram.article.service.AdminArticleService;
import com.minprogram.common.api.ApiResponse;
import com.minprogram.common.security.UserContext;
import com.minprogram.user.service.AdminGuardService;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/articles")
public class AdminArticleController {
    private final AdminArticleService adminArticleService;
    private final AdminGuardService adminGuardService;

    public AdminArticleController(AdminArticleService adminArticleService, AdminGuardService adminGuardService) {
        this.adminArticleService = adminArticleService;
        this.adminGuardService = adminGuardService;
    }

    @PostMapping
    public ApiResponse<ArticleDetailResponse> createOfficial(@Valid @RequestBody CreateOfficialArticleRequest req) {
        Long userId = UserContext.getUserId();
        if (userId == null) {
            return ApiResponse.fail("未登录");
        }
        adminGuardService.checkAdmin(userId);
        return ApiResponse.ok(adminArticleService.createOfficial(userId, req));
    }
}
