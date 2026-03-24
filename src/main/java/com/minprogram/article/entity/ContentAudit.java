package com.minprogram.article.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "content_audit")
public class ContentAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "target_type", nullable = false)
    private Integer targetType;
    @Column(name = "target_id", nullable = false)
    private Long targetId;
    @Column(name = "submit_user_id", nullable = false)
    private Long submitUserId;
    @Column(nullable = false)
    private Integer status;
    @Column(length = 255)
    private String reason;
    @Column(name = "reviewer_id")
    private Long reviewerId;
    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        if (status == null) {
            status = 0;
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getTargetType() { return targetType; }
    public void setTargetType(Integer targetType) { this.targetType = targetType; }
    public Long getTargetId() { return targetId; }
    public void setTargetId(Long targetId) { this.targetId = targetId; }
    public Long getSubmitUserId() { return submitUserId; }
    public void setSubmitUserId(Long submitUserId) { this.submitUserId = submitUserId; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public Long getReviewerId() { return reviewerId; }
    public void setReviewerId(Long reviewerId) { this.reviewerId = reviewerId; }
    public LocalDateTime getReviewedAt() { return reviewedAt; }
    public void setReviewedAt(LocalDateTime reviewedAt) { this.reviewedAt = reviewedAt; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
