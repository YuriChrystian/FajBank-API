package br.com.faj.bank.invoice.domain;

import br.com.faj.bank.core.SessionCustomer;
import br.com.faj.bank.invoice.data.InvoiceRepository;
import br.com.faj.bank.invoice.model.entity.InvoiceChargeEntity;
import br.com.faj.bank.invoice.model.entity.InvoiceEntity;
import br.com.faj.bank.invoice.model.request.AddChargeRequest;
import br.com.faj.bank.invoice.model.response.InvoiceResponse;
import br.com.faj.bank.invoice.model.response.InvoiceChargeResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class AddChargeUseCase {

    private final InvoiceRepository invoiceRepository;
    private final SessionCustomer sessionCustomer;

    public AddChargeUseCase(
            InvoiceRepository invoiceRepository,
            SessionCustomer sessionCustomer
    ) {
        this.invoiceRepository = invoiceRepository;
        this.sessionCustomer = sessionCustomer;
    }

    public InvoiceResponse addCharge(Long invoiceId, AddChargeRequest request) {
        InvoiceEntity invoice = invoiceRepository.findByIdAndCustomerId(invoiceId, sessionCustomer.getCustomerId());
        
        if (invoice == null) {
            return null;
        }

        InvoiceChargeEntity charge = new InvoiceChargeEntity();
        charge.setDescription(request.description());
        charge.setAmount(new BigDecimal(request.amount()));
        charge.setChargeDate(LocalDateTime.now());
        charge.setInvoice(invoice);

        invoice.getCharges().add(charge);
        
        // Atualiza o valor total da fatura
        BigDecimal newTotal = invoice.getTotalAmount().add(new BigDecimal(request.amount()));
        invoice.setTotalAmount(newTotal);

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