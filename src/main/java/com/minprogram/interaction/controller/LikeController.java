package com.minprogram.interaction.controller;

import com.minprogram.common.api.ApiResponse;
import com.minprogram.common.security.UserContext;
import com.minprogram.interaction.dto.ToggleActionRequest;
import com.minprogram.interaction.dto.ToggleActionResponse;
import com.minprogram.interaction.entity.LikeRecord;
import com.minprogram.interaction.repository.LikeRecordRepository;
import com.minprogram.interaction.service.InteractionCounterService;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    private final LikeRecordRepository likeRecordRepository;
    private final InteractionCounterService interactionCounterService;

    public LikeController(LikeRecordRepository likeRecordRepository, InteractionCounterService interactionCounterService) {
        this.likeRecordRepository = likeRecordRepository;
        this.interactionCounterService = interactionCounterService;
    }

    @PostMapping("/toggle")
    public ApiResponse<ToggleActionResponse> toggle(@Valid @RequestBody ToggleActionRequest req) {
        Long userId = UserContext.getUserId();
        if (userId == null) {
            return ApiResponse.fail("未登录");
        }
        Optional<LikeRecord> existing = likeRecordRepository.findByUserIdAndTargetTypeAndTargetId(userId, req.getTargetType(), req.getTargetId());
        if (existing.isPresent()) {
            likeRecordRepository.delete(existing.get());
            interactionCounterService.refreshCounts(req.getTargetType(), req.getTargetId());
            return ApiResponse.ok(new ToggleActionResponse(false));
        }
        LikeRecord record = new LikeRecord();
        record.setUserId(userId);
        record.setTargetType(req.getTargetType());
        record.setTargetId(req.getTargetId());
        likeRecordRepository.save(record);
        interactionCounterService.refreshCounts(req.getTargetType(), req.getTargetId());
        return ApiResponse.ok(new ToggleActionResponse(true));
    }
}
