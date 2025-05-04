package br.com.faj.bank.invoice.domain;

import br.com.faj.bank.core.SessionCustomer;
import br.com.faj.bank.invoice.data.InvoiceRepository;
import br.com.faj.bank.invoice.model.entity.InvoiceChargeEntity;
import br.com.faj.bank.invoice.model.entity.InvoiceEntity;
import br.com.faj.bank.invoice.model.request.CreateInvoiceRequest;
import br.com.faj.bank.invoice.model.response.InvoiceResponse;
import br.com.faj.bank.invoice.model.response.InvoiceChargeResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class CreateInvoiceUseCase {

    private final InvoiceRepository invoiceRepository;
    private final SessionCustomer sessionCustomer;

    public CreateInvoiceUseCase(
            InvoiceRepository invoiceRepository,
            SessionCustomer sessionCustomer
    ) {
        this.invoiceRepository = invoiceRepository;
        this.sessionCustomer = sessionCustomer;
    }

    public InvoiceResponse create(CreateInvoiceRequest request) {
        InvoiceEntity invoice = new InvoiceEntity();
        invoice.setCustomerId(sessionCustomer.getCustomerId());
        invoice.setDueDate(request.dueDate());
        invoice.setTotalAmount(new BigDecimal(request.amount()));
        invoice.setStatus("PENDING");

        InvoiceChargeEntity charge = new InvoiceChargeEntity();
        charge.setDescription(request.description());
        charge.setAmount(new BigDecimal(request.amount()));
        charge.setChargeDate(LocalDateTime.now());
        charge.setInvoice(invoice);

        List<InvoiceChargeEntity> charges = new ArrayList<>();
        charges.add(charge);
        invoice.setCharges(charges);

        invoice = invoiceRepository.save(invoice);

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