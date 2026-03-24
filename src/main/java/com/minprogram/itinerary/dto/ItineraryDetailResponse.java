package com.minprogram.itinerary.dto;

import java.util.ArrayList;
import java.util.List;

public class ItineraryDetailResponse {
    private Long id;
    private String title;
    private Long destinationId;
    private Integer days;
    private Integer travelMode;
    private String preferenceJson;
    private Integer isPublic;
    private List<Item> items = new ArrayList<>();

    public static class Item {
        private Long id;
        private Integer dayNo;
        private Integer sortNo;
        private Long attractionId;
        private String startTime;
        private String endTime;
        private String title;
        private String note;
        private String transportTip;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
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

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Long getDestinationId() { return destinationId; }
    public void setDestinationId(Long destinationId) { this.destinationId = destinationId; }
    public Integer getDays() { return days; }
    public void setDays(Integer days) { this.days = days; }
    public Integer getTravelMode() { return travelMode; }
    public void setTravelMode(Integer travelMode) { this.travelMode = travelMode; }
    public String getPreferenceJson() { return preferenceJson; }
    public void setPreferenceJson(String preferenceJson) { this.preferenceJson = preferenceJson; }
    public Integer getIsPublic() { return isPublic; }
    public void setIsPublic(Integer isPublic) { this.isPublic = isPublic; }
    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { this.items = items; }
}
