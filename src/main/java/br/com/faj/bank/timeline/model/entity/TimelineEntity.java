package br.com.faj.bank.timeline.model.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "timeline")
public class TimelineEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long subTimelineId;

    private Integer typeSubTimeline;

    private Long customerId;

    @Temporal(TemporalType.DATE)
    private Date registredIn;

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

    public Date getRegistredIn() {
        return registredIn;
    }

    public void setRegistredIn(Date registredIn) {
        this.registredIn = registredIn;
    }
}
