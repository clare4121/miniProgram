package com.minprogram.interaction.dto;

public class ToggleActionResponse {
    private Boolean active;

    public ToggleActionResponse() {
    }

    public ToggleActionResponse(Boolean active) {
        this.active = active;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
