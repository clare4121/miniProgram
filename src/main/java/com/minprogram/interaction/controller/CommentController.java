package com.minprogram.interaction.controller;

import com.minprogram.common.api.ApiResponse;
import com.minprogram.common.security.UserContext;
import com.minprogram.interaction.dto.CreateCommentRequest;
import com.minprogram.interaction.entity.Comment;
import com.minprogram.interaction.repository.CommentRepository;
import com.minprogram.interaction.service.InteractionCounterService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentRepository commentRepository;
    private final InteractionCounterService interactionCounterService;

    public CommentController(CommentRepository commentRepository, InteractionCounterService interactionCounterService) {
        this.commentRepository = commentRepository;
        this.interactionCounterService = interactionCounterService;
    }

    @PostMapping
    public ApiResponse<Comment> create(@Valid @RequestBody CreateCommentRequest req) {
        Long userId = UserContext.getUserId();
        if (userId == null) {
            return ApiResponse.fail("未登录");
        }
        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setTargetType(req.getTargetType());
        comment.setTargetId(req.getTargetId());
        comment.setContent(req.getContent());
        comment = commentRepository.save(comment);
        interactionCounterService.refreshCounts(req.getTargetType(), req.getTargetId());
        return ApiResponse.ok(comment);
    }

    @GetMapping
    public ApiResponse<List<Comment>> list(@RequestParam Integer targetType, @RequestParam Long targetId) {
        return ApiResponse.ok(commentRepository.findByTargetTypeAndTargetIdAndStatusOrderByIdDesc(targetType, targetId, 1));
    }
}
