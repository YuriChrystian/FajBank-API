package br.com.faj.bank.timeline.domain;

import br.com.faj.bank.core.SessionCustomer;
import br.com.faj.bank.timeline.data.TimelineCustomerRepository;
import br.com.faj.bank.timeline.data.TimelineRepository;
import br.com.faj.bank.timeline.model.TimelineCustomerType;
import br.com.faj.bank.timeline.model.TimelineType;
import br.com.faj.bank.timeline.model.entity.TimelineCustomerEntity;
import br.com.faj.bank.timeline.model.entity.TimelineEntity;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class RegisterCustomerTimelineUseCase {

    private final TimelineCustomerRepository timelineCustomerRepository;
    private final TimelineRepository customerRepository;
    private final SessionCustomer session;

    public RegisterCustomerTimelineUseCase(
            TimelineCustomerRepository timelineCustomerRepository,
            TimelineRepository customerRepository,
            SessionCustomer session
    ) {
        this.timelineCustomerRepository = timelineCustomerRepository;
        this.customerRepository = customerRepository;
        this.session = session;
    }


    public void registerNewCustomer(Long customerId) {

        TimelineCustomerEntity customer = new TimelineCustomerEntity();
        customer.setCustomerId(customerId);
        customer.setType(TimelineCustomerType.NEW_CUSTOMER.type);
        customer.setRegistredIn(Date.from(Instant.now()));

        TimelineCustomerEntity register = timelineCustomerRepository.save(customer);

        customerRepository.save(createTimelineEntity(customerId, register.getId(), TimelineType.CUSTOMER));
    }

    public void registerUpdateProfile() {
        TimelineCustomerEntity customer = new TimelineCustomerEntity();
        customer.setCustomerId(session.getCustomerId());
        customer.setType(TimelineCustomerType.UPDATE_PROFILE.type);
        customer.setRegistredIn(Date.from(Instant.now()));

        TimelineCustomerEntity register = timelineCustomerRepository.save(customer);

        customerRepository.save(createTimelineEntity(session.getCustomerId(), register.getId(), TimelineType.CUSTOMER));
    }

    private TimelineEntity createTimelineEntity(Long customerId, Long subTimelineId, TimelineType type) {
        TimelineEntity timeline = new TimelineEntity();
        timeline.setCustomerId(customerId);
        timeline.setSubTimelineId(subTimelineId);
        timeline.setRegistredIn(Date.from(Instant.now()));
        timeline.setTypeSubTimeline(type.ordinal());

        return timeline;
    }
}
