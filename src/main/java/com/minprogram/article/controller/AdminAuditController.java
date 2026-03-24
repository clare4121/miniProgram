package com.minprogram.article.controller;

import com.minprogram.article.dto.AdminAuditRequest;
import com.minprogram.article.entity.ContentAudit;
import com.minprogram.article.service.AdminAuditService;
import com.minprogram.common.api.ApiResponse;
import com.minprogram.common.security.UserContext;
import com.minprogram.user.service.AdminGuardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/audits")
public class AdminAuditController {
    private final AdminAuditService adminAuditService;
    private final AdminGuardService adminGuardService;

    public AdminAuditController(AdminAuditService adminAuditService, AdminGuardService adminGuardService) {
        this.adminAuditService = adminAuditService;
        this.adminGuardService = adminGuardService;
    }

    @GetMapping
    public ApiResponse<List<ContentAudit>> listPending() {
        Long reviewerId = UserContext.getUserId();
        if (reviewerId == null) {
            return ApiResponse.fail("未登录");
        }
        adminGuardService.checkAdmin(reviewerId);
        return ApiResponse.ok(adminAuditService.listPending());
    }

    @PostMapping("/{id}/approve")
    public ApiResponse<Object> approve(@PathVariable("id") Long id) {
        Long reviewerId = UserContext.getUserId();
        if (reviewerId == null) {
            return ApiResponse.fail("未登录");
        }
        adminGuardService.checkAdmin(reviewerId);
        adminAuditService.approve(id, reviewerId);
        return ApiResponse.ok(null);
    }

    @PostMapping("/{id}/reject")
    public ApiResponse<Object> reject(@PathVariable("id") Long id, @RequestBody(required = false) AdminAuditRequest req) {
        Long reviewerId = UserContext.getUserId();
        if (reviewerId == null) {
            return ApiResponse.fail("未登录");
        }
        adminGuardService.checkAdmin(reviewerId);
        String reason = req == null ? null : req.getReason();
        adminAuditService.reject(id, reviewerId, reason);
        return ApiResponse.ok(null);
    }
}
