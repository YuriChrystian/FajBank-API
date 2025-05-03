package br.com.faj.bank.timeline.domain;

import br.com.faj.bank.core.SessionCustomer;
import br.com.faj.bank.timeline.data.TimelineCustomerRepository;
import br.com.faj.bank.timeline.data.TimelineRepository;
import br.com.faj.bank.timeline.model.TimelineCustomerType;
import br.com.faj.bank.timeline.model.TimelineType;
import br.com.faj.bank.timeline.model.domain.TimelineCustomerDataDomain;
import br.com.faj.bank.timeline.model.domain.TimelineDomain;
import br.com.faj.bank.timeline.model.entity.TimelineCustomerEntity;
import br.com.faj.bank.timeline.model.entity.TimelineEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static br.com.faj.bank.timeline.model.TimelineType.CUSTOMER;

@Component
public class FetchTimelineUseCase {

    private final TimelineCustomerRepository customerRepository;
    private final TimelineRepository timelineRepository;
    private final SessionCustomer session;

    public FetchTimelineUseCase(
            TimelineCustomerRepository customerRepository,
            TimelineRepository timelineRepository,
            SessionCustomer session
    ) {
        this.session = session;
        this.customerRepository = customerRepository;
        this.timelineRepository = timelineRepository;
    }

    // Implementar filtro no futuro
    public List<TimelineDomain> getTimeline() {
        List<TimelineDomain> timelineDomains = new ArrayList<>();

        var timeline = timelineRepository.findAllByCustomerIdOrderByRegistredInAsc(session.getCustomerId());
        var subTimelines = customerRepository.findByCustomerId(session.getCustomerId());

        for (TimelineEntity timelineEntity : timeline) {
            recoveryDataTemplate(timelineDomains, subTimelines, timelineEntity);
        }

        return timelineDomains;
     }

    private void recoveryDataTemplate(
            List<TimelineDomain> timeline,
            List<TimelineCustomerEntity> timelineCustomer,
            TimelineEntity timelineEntity
    ) {
        switch (TimelineType.values()[timelineEntity.getTypeSubTimeline()]) {
            case CUSTOMER:
                var item = timelineCustomer
                        .stream()
                        .filter(data -> Objects.equals(data.getId(), timelineEntity.getId()))
                        .findFirst();

                item.ifPresent(timelineCustomerEntity -> timeline.add(
                        new TimelineDomain(
                                CUSTOMER,
                                timelineCustomerEntity.getRegistredIn(),
                                recoverySubCustomerTemplate(timelineCustomerEntity)
                        )
                ));
                break;
            default:
                System.out.println("Invalid sub timeline type");
                break;
        }
    }

    private TimelineCustomerDataDomain recoverySubCustomerTemplate(
            TimelineCustomerEntity item
    ) {
        return switch (TimelineCustomerType.values()[item.getType()]) {
            case NEW_CUSTOMER -> new TimelineCustomerDataDomain("Ingressou no aplicativo");
            case UPDATE_PROFILE -> new TimelineCustomerDataDomain("Atualizou dados");
        };
    }
}
