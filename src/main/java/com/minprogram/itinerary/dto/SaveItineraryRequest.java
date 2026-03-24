package com.minprogram.itinerary.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SaveItineraryRequest {
    @NotBlank(message = "title不能为空")
    private String title;
    @NotNull(message = "destinationId不能为空")
    private Long destinationId;
    @Min(value = 1, message = "days最小1")
    @Max(value = 15, message = "days最大15")
    private Integer days;
    @NotNull(message = "travelMode不能为空")
    private Integer travelMode;
    private List<String> preferences = new ArrayList<>();
    private Integer isPublic = 0;
    private List<Item> items = new ArrayList<>();

    public static class Item {
        @NotNull(message = "dayNo不能为空")
        private Integer dayNo;
        @NotNull(message = "sortNo不能为空")
        private Integer sortNo;
        private Long attractionId;
        private String startTime;
        private String endTime;
        @NotBlank(message = "title不能为空")
        private String title;
        private String note;
        private String transportTip;

        public Integer getDayNo() { return dayNo; }
        public void setDayNo(Integer dayNo) { this.dayNo = dayNo; }
        public Integer getSortNo() { return sortNo; }
        public void setSortNo(Integer sortNo) { this.sortNo = sortNo; }
        public Long getAttractionId() { return attractionId; }
        public void setAttractionId(Long attractionId) { this.attractionId = attractionId; }
        public String getStartTime() { return startTime; }
        public void setStartTime(String startTime) { this.startTime = startTime; }
        public String getEndTime() { return endTime; }
        public void setEndTime(String endTime) { this.endTime = endTime; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getNote() { return note; }
        public void setNote(String note) { this.note = note; }
        public String getTransportTip() { return transportTip; }
        public void setTransportTip(String transportTip) { this.transportTip = transportTip; }
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Long getDestinationId() { return destinationId; }
    public void setDestinationId(Long destinationId) { this.destinationId = destinationId; }
    public Integer getDays() { return days; }
    public void setDays(Integer days) { this.days = days; }
    public Integer getTravelMode() { return travelMode; }
    public void setTravelMode(Integer travelMode) { this.travelMode = travelMode; }
    public List<String> getPreferences() { return preferences; }
    public void setPreferences(List<String> preferences) { this.preferences = preferences; }
    public Integer getIsPublic() { return isPublic; }
    public void setIsPublic(Integer isPublic) { this.isPublic = isPublic; }
    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { this.items = items; }
}
