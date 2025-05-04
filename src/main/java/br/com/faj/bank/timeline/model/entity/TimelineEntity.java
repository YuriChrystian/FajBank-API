package br.com.faj.bank.timeline.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "timeline")
public class TimelineEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long subTimelineId;

    private Integer typeSubTimeline;

    private Long customerId;

    private LocalDateTime registredIn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSubTimelineId() {
        return subTimelineId;
    }

    public void setSubTimelineId(Long subTimelineId) {
        this.subTimelineId = subTimelineId;
    }

    public Integer getTypeSubTimeline() {
        return typeSubTimeline;
    }

    public void setTypeSubTimeline(Integer typeSubTimeline) {
        this.typeSubTimeline = typeSubTimeline;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getRegistredIn() {
        return registredIn;
    }

    public void setRegistredIn(LocalDateTime registredIn) {
        this.registredIn = registredIn;
    }
}
