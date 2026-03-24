package com.minprogram.itinerary.dto;

public class SaveItineraryResponse {
    private Long itineraryId;

    public SaveItineraryResponse() {
    }

    public SaveItineraryResponse(Long itineraryId) {
        this.itineraryId = itineraryId;
    }

    public Long getItineraryId() {
        return itineraryId;
    }

    public void setItineraryId(Long itineraryId) {
        this.itineraryId = itineraryId;
    }
}
