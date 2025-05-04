package br.com.faj.bank.timeline.domain;

import br.com.faj.bank.core.SessionCustomer;
import br.com.faj.bank.timeline.data.TimelineInvoiceRepository;
import br.com.faj.bank.timeline.model.TimelineInvoiceType;
import br.com.faj.bank.timeline.model.entity.TimelineInvoiceEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class RegisterInvoiceTimelineUseCase {

    private final TimelineInvoiceRepository timelineInvoiceRepository;
    private final SessionCustomer session;

    public RegisterInvoiceTimelineUseCase(
            SessionCustomer session,
            TimelineInvoiceRepository timelineInvoiceRepository
    ) {
        this.session = session;
        this.timelineInvoiceRepository = timelineInvoiceRepository;
    }

    public void registerNewInvoice(Long invoiceId, String description, BigDecimal amount) {
        TimelineInvoiceEntity timeline = new TimelineInvoiceEntity();
        timeline.setCustomerId(session.getCustomerId());
        timeline.setInvoiceId(invoiceId);
        timeline.setDescription(description);
        timeline.setAmount(amount);
        timeline.setType(TimelineInvoiceType.INVOICE_CREATED.name());
        timeline.setRegisteredIn(LocalDateTime.now());

        timelineInvoiceRepository.save(timeline);
    }

    public void registerNewCharge(Long invoiceId, String description, BigDecimal amount) {
        TimelineInvoiceEntity timeline = new TimelineInvoiceEntity();
        timeline.setCustomerId(session.getCustomerId());
        timeline.setInvoiceId(invoiceId);
        timeline.setDescription(description);
        timeline.setAmount(amount);
        timeline.setType(TimelineInvoiceType.CHARGE_ADDED.name());
        timeline.setRegisteredIn(LocalDateTime.now());

        timelineInvoiceRepository.save(timeline);
    }
} 