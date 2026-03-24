package com.minprogram.user.service;

import com.minprogram.user.entity.User;
import com.minprogram.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminGuardService {
    private final UserRepository userRepository;

    public AdminGuardService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void checkAdmin(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("用户不存在"));
        if (!Integer.valueOf(1).equals(user.getRole())) {
            throw new IllegalArgumentException("无管理员权限");
        }
    }
}
