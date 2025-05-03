package br.com.faj.bank.timeline.model.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "timeline_customer")
public class TimelineCustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;

    @Temporal(TemporalType.DATE)
    private Date registredIn;

    private Integer type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
