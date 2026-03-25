package com.minprogram.image.controller;

import com.minprogram.common.api.ApiResponse;
import com.minprogram.image.dto.ImageResponse;
import com.minprogram.image.entity.Image;
import com.minprogram.image.service.ImageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping
    public ApiResponse<ImageResponse> create(@RequestBody ImageRequest request) {
        Image image = imageService.saveImage(request.getImageUrl(), request.getTargetType(), request.getTargetId());
        return ApiResponse.ok(toResponse(image));
    }

    @GetMapping("/target")
    public ApiResponse<List<ImageResponse>> getByTarget(@RequestParam Integer targetType, @RequestParam Long targetId) {
        List<Image> images = imageService.getImagesByTarget(targetType, targetId);
        List<ImageResponse> list = images.stream().map(this::toResponse).collect(Collectors.toList());
        return ApiResponse.ok(list);
    }

    @GetMapping("/{id}")
    public ApiResponse<ImageResponse> getById(@PathVariable Long id) {
        Image image = imageService.getImageById(id);
        return ApiResponse.ok(toResponse(image));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        imageService.deleteImage(id);
        return ApiResponse.ok(null);
    }

    private ImageResponse toResponse(Image image) {
        ImageResponse resp = new ImageResponse();
        resp.setId(image.getId());
        resp.setImageUrl(image.getImageUrl());
        resp.setTargetType(image.getTargetType());
        resp.setTargetId(image.getTargetId());
        resp.setCreatedAt(image.getCreatedAt());
        return resp;
    }

    public static class ImageRequest {
        private String imageUrl;
        private Integer targetType;
        private Long targetId;

        public String getImageUrl() { return imageUrl; }
        public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
        public Integer getTargetType() { return targetType; }
        public void setTargetType(Integer targetType) { this.targetType = targetType; }
        public Long getTargetId() { return targetId; }
        public void setTargetId(Long targetId) { this.targetId = targetId; }
    }
}