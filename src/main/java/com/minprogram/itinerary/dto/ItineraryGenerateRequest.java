package com.minprogram.itinerary.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import java.util.List;

public class ItineraryGenerateRequest {
    @NotNull(message = "destinationId不能为空")
    private Long destinationId;

    @Min(value = 1, message = "days最小1")
    @Max(value = 15, message = "days最大15")
    private Integer days;

    @NotNull(message = "travelMode不能为空")
    private Integer travelMode; // 1步行 2公共交通 3自驾

    private List<String> preferences;

    public Long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Long destinationId) {
        this.destinationId = destinationId;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(Integer travelMode) {
        this.travelMode = travelMode;
    }

    public List<String> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<String> preferences) {
        this.preferences = preferences;
    }
}
