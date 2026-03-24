package com.minprogram.interaction.controller;

import com.minprogram.common.api.ApiResponse;
import com.minprogram.common.security.UserContext;
import com.minprogram.interaction.dto.ToggleActionRequest;
import com.minprogram.interaction.dto.ToggleActionResponse;
import com.minprogram.interaction.entity.Favorite;
import com.minprogram.interaction.repository.FavoriteRepository;
import com.minprogram.interaction.service.InteractionCounterService;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteRepository favoriteRepository;
    private final InteractionCounterService interactionCounterService;

    public FavoriteController(FavoriteRepository favoriteRepository, InteractionCounterService interactionCounterService) {
        this.favoriteRepository = favoriteRepository;
        this.interactionCounterService = interactionCounterService;
    }

    @PostMapping("/toggle")
    public ApiResponse<ToggleActionResponse> toggle(@Valid @RequestBody ToggleActionRequest req) {
        Long userId = UserContext.getUserId();
        if (userId == null) {
            return ApiResponse.fail("未登录");
        }
        Optional<Favorite> existing = favoriteRepository.findByUserIdAndTargetTypeAndTargetId(userId, req.getTargetType(), req.getTargetId());
        if (existing.isPresent()) {
            favoriteRepository.delete(existing.get());
            interactionCounterService.refreshCounts(req.getTargetType(), req.getTargetId());
            return ApiResponse.ok(new ToggleActionResponse(false));
        }
        Favorite record = new Favorite();
        record.setUserId(userId);
        record.setTargetType(req.getTargetType());
        record.setTargetId(req.getTargetId());
        favoriteRepository.save(record);
        interactionCounterService.refreshCounts(req.getTargetType(), req.getTargetId());
        return ApiResponse.ok(new ToggleActionResponse(true));
    }
}
