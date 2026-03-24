package com.minprogram.interaction.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateCommentRequest {
    @NotNull(message = "targetType不能为空")
    private Integer targetType;
    @NotNull(message = "targetId不能为空")
    private Long targetId;
    @NotBlank(message = "content不能为空")
    private String content;

    public Integer getTargetType() { return targetType; }
    public void setTargetType(Integer targetType) { this.targetType = targetType; }
    public Long getTargetId() { return targetId; }
    public void setTargetId(Long targetId) { this.targetId = targetId; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
