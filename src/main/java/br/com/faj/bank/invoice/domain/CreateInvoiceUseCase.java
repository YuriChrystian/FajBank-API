package br.com.faj.bank.invoice.domain;

import br.com.faj.bank.core.SessionCustomer;
import br.com.faj.bank.invoice.data.InvoiceRepository;
import br.com.faj.bank.invoice.model.entity.InvoiceChargeEntity;
import br.com.faj.bank.invoice.model.entity.InvoiceEntity;
import br.com.faj.bank.invoice.model.request.CreateInvoiceRequest;
import br.com.faj.bank.invoice.model.response.InvoiceResponse;
import br.com.faj.bank.invoice.model.response.InvoiceChargeResponse;
import br.com.faj.bank.timeline.domain.RegisterInvoiceTimelineUseCase;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class CreateInvoiceUseCase {

    private final InvoiceRepository invoiceRepository;
    private final SessionCustomer sessionCustomer;
    private final RegisterInvoiceTimelineUseCase registerInvoiceTimelineUseCase;

    public CreateInvoiceUseCase(
            InvoiceRepository invoiceRepository,
            SessionCustomer sessionCustomer,
            RegisterInvoiceTimelineUseCase registerInvoiceTimelineUseCase
    ) {
        this.invoiceRepository = invoiceRepository;
        this.sessionCustomer = sessionCustomer;
        this.registerInvoiceTimelineUseCase = registerInvoiceTimelineUseCase;
    }

    public InvoiceResponse create(CreateInvoiceRequest request) {
        InvoiceEntity invoice = new InvoiceEntity();
        invoice.setCustomerId(sessionCustomer.getCustomerId());
        invoice.setDueDate(request.dueDate());
        invoice.setTotalAmount(request.amount());
        invoice.setStatus("PENDING");

        InvoiceChargeEntity charge = new InvoiceChargeEntity();
        charge.setDescription(request.description());
        charge.setAmount(request.amount());
        charge.setChargeDate(LocalDateTime.now());
        charge.setInvoice(invoice);

        List<InvoiceChargeEntity> charges = new ArrayList<>();
        charges.add(charge);
        invoice.setCharges(charges);

        invoice = invoiceRepository.save(invoice);

        // Registra o evento na timeline
        registerInvoiceTimelineUseCase.registerNewInvoice(
            invoice.getId(),
            request.description(),
            request.amount()
        );

        return convertToResponse(invoice);
    }

    private InvoiceResponse convertToResponse(InvoiceEntity invoice) {
        List<InvoiceChargeResponse> chargeResponses = invoice.getCharges().stream()
                .map(charge -> new InvoiceChargeResponse(
                        charge.getId(),
                        charge.getDescription(),
                        charge.getAmount(),
                        charge.getChargeDate()
                ))
                .toList();

        return new InvoiceResponse(
                invoice.getId(),
                invoice.getDueDate(),
                invoice.getTotalAmount(),
                invoice.getStatus(),
                chargeResponses
        );
    }
} 