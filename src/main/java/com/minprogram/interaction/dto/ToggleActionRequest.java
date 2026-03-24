package com.minprogram.interaction.dto;

import jakarta.validation.constraints.NotNull;

public class ToggleActionRequest {
    @NotNull(message = "targetType不能为空")
    private Integer targetType;
    @NotNull(message = "targetId不能为空")
    private Long targetId;

    public Integer getTargetType() { return targetType; }
    public void setTargetType(Integer targetType) { this.targetType = targetType; }
    public Long getTargetId() { return targetId; }
    public void setTargetId(Long targetId) { this.targetId = targetId; }
}
