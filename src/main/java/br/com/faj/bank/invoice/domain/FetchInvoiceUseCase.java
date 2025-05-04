package br.com.faj.bank.invoice.domain;

import br.com.faj.bank.core.SessionCustomer;
import br.com.faj.bank.invoice.data.InvoiceRepository;
import br.com.faj.bank.invoice.model.entity.InvoiceEntity;
import br.com.faj.bank.invoice.model.response.InvoiceResponse;
import br.com.faj.bank.invoice.model.response.InvoiceChargeResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FetchInvoiceUseCase {

    private final InvoiceRepository invoiceRepository;
    private final SessionCustomer sessionCustomer;

    public FetchInvoiceUseCase(
            InvoiceRepository invoiceRepository,
            SessionCustomer sessionCustomer
    ) {
        this.invoiceRepository = invoiceRepository;
        this.sessionCustomer = sessionCustomer;
    }

    public List<InvoiceResponse> fetchAll() {
        List<InvoiceEntity> invoices = invoiceRepository.findAllByCustomerId(sessionCustomer.getCustomerId());
        return invoices.stream()
                .map(this::convertToResponse)
                .toList();
    }

    public InvoiceResponse fetchById(Long invoiceId) {
        InvoiceEntity invoice = invoiceRepository.findByIdAndCustomerId(invoiceId, sessionCustomer.getCustomerId());
        if (invoice == null) {
            return null;
        }
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