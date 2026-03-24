package com.minprogram.auth.service;

import com.minprogram.auth.dto.LoginByCodeRequest;
import com.minprogram.auth.dto.LoginResponse;
import com.minprogram.common.security.JwtUtil;
import com.minprogram.infra.wx.WxApiClient;
import com.minprogram.infra.wx.dto.WxCode2SessionResp;
import com.minprogram.user.entity.User;
import com.minprogram.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class AuthService {

    private final WxApiClient wxApiClient;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthService(WxApiClient wxApiClient, UserRepository userRepository, JwtUtil jwtUtil) {
        this.wxApiClient = wxApiClient;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public LoginResponse loginByCode(LoginByCodeRequest req) {
        WxCode2SessionResp wxResp = wxApiClient.code2Session(req.getCode());
        if (wxResp == null || wxResp.getErrcode() != null || !StringUtils.hasText(wxResp.getOpenid())) {
            String reason = wxResp == null ? "empty response" : wxResp.getErrmsg();
            throw new IllegalArgumentException("微信登录失败: " + reason);
        }

        User user = userRepository.findByOpenid(wxResp.getOpenid()).orElse(null);
        boolean isNew = false;
        if (user == null) {
            isNew = true;
            user = new User();
            user.setOpenid(wxResp.getOpenid());
            user.setNickname(StringUtils.hasText(req.getNickname()) ? req.getNickname() : "微信用户");
            user.setAvatarUrl(req.getAvatarUrl());
            user = userRepository.save(user);
        } else {
            boolean needUpdate = false;
            if (StringUtils.hasText(req.getNickname()) && !req.getNickname().equals(user.getNickname())) {
                user.setNickname(req.getNickname());
                needUpdate = true;
            }
            if (StringUtils.hasText(req.getAvatarUrl()) && !req.getAvatarUrl().equals(user.getAvatarUrl())) {
                user.setAvatarUrl(req.getAvatarUrl());
                needUpdate = true;
            }
            if (needUpdate) {
                user = userRepository.save(user);
            }
        }

        LoginResponse resp = new LoginResponse();
        resp.setToken(jwtUtil.generateToken(user.getId()));
        resp.setUserId(user.getId());
        resp.setNickname(user.getNickname());
        resp.setAvatarUrl(user.getAvatarUrl());
        resp.setIsNewUser(isNew);
        return resp;
    }
}
