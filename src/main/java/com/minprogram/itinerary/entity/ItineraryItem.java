package com.minprogram.itinerary.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "itinerary_item")
public class ItineraryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "itinerary_id", nullable = false)
    private Long itineraryId;

    @Column(name = "day_no", nullable = false)
    private Integer dayNo;

    @Column(name = "sort_no", nullable = false)
    private Integer sortNo;

    @Column(name = "attraction_id")
    private Long attractionId;

    @Column(name = "start_time", length = 16)
    private String startTime;

    @Column(name = "end_time", length = 16)
    private String endTime;

    @Column(nullable = false, length = 128)
    private String title;

    @Column(length = 512)
    private String note;

    @Column(name = "transport_tip", length = 255)
    private String transportTip;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getItineraryId() { return itineraryId; }
    public void setItineraryId(Long itineraryId) { this.itineraryId = itineraryId; }
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
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
