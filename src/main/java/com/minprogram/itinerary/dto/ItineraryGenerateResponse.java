package com.minprogram.itinerary.dto;

import java.util.ArrayList;
import java.util.List;

public class ItineraryGenerateResponse {
    private Long destinationId;
    private Integer days;
    private Integer travelMode;
    private List<String> preferences = new ArrayList<>();
    private List<DayPlan> plan = new ArrayList<>();

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

    public List<DayPlan> getPlan() {
        return plan;
    }

    public void setPlan(List<DayPlan> plan) {
        this.plan = plan;
    }

    public static class DayPlan {
        private Integer dayNo;
        private List<PlanItem> items = new ArrayList<>();

        public Integer getDayNo() {
            return dayNo;
        }

        public void setDayNo(Integer dayNo) {
            this.dayNo = dayNo;
        }

        public List<PlanItem> getItems() {
            return items;
        }

        public void setItems(List<PlanItem> items) {
            this.items = items;
        }
    }

    public static class PlanItem {
        private String timeRange;
        private String title;
        private String note;

        public String getTimeRange() {
            return timeRange;
        }

        public void setTimeRange(String timeRange) {
            this.timeRange = timeRange;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }
    }
}
