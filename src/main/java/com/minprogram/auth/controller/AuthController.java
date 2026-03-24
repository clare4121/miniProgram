package com.minprogram.auth.controller;

import com.minprogram.auth.dto.LoginByCodeRequest;
import com.minprogram.auth.dto.LoginResponse;
import com.minprogram.auth.service.AuthService;
import com.minprogram.common.api.ApiResponse;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/wx-login")
    public ApiResponse<LoginResponse> wxLogin(@Valid @RequestBody LoginByCodeRequest req) {
        return ApiResponse.ok(authService.loginByCode(req));
    }
}
