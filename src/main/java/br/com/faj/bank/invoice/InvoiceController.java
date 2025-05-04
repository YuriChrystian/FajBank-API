package br.com.faj.bank.invoice;

import br.com.faj.bank.invoice.domain.AddChargeUseCase;
import br.com.faj.bank.invoice.domain.CreateInvoiceUseCase;
import br.com.faj.bank.invoice.domain.FetchInvoiceUseCase;
import br.com.faj.bank.invoice.model.request.AddChargeRequest;
import br.com.faj.bank.invoice.model.request.CreateInvoiceRequest;
import br.com.faj.bank.invoice.model.response.InvoiceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/invoice")
public class InvoiceController {

    private final CreateInvoiceUseCase createInvoiceUseCase;
    private final FetchInvoiceUseCase fetchInvoiceUseCase;
    private final AddChargeUseCase addChargeUseCase;

    public InvoiceController(
            CreateInvoiceUseCase createInvoiceUseCase,
            FetchInvoiceUseCase fetchInvoiceUseCase,
            AddChargeUseCase addChargeUseCase
    ) {
        this.createInvoiceUseCase = createInvoiceUseCase;
        this.fetchInvoiceUseCase = fetchInvoiceUseCase;
        this.addChargeUseCase = addChargeUseCase;
    }

    @PostMapping
    public ResponseEntity<InvoiceResponse> createInvoice(@RequestBody CreateInvoiceRequest request) {
        if (!request.isValidData()) {
            return ResponseEntity.badRequest().build();
        }

        InvoiceResponse response = createInvoiceUseCase.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<InvoiceResponse>> getAllInvoices() {
        List<InvoiceResponse> invoices = fetchInvoiceUseCase.fetchAll();
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/{invoiceId}")
    public ResponseEntity<InvoiceResponse> getInvoiceById(@PathVariable Long invoiceId) {
        InvoiceResponse invoice = fetchInvoiceUseCase.fetchById(invoiceId);
        if (invoice == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(invoice);
    }

    @PostMapping("/{invoiceId}/charges")
    public ResponseEntity<InvoiceResponse> addCharge(
            @PathVariable Long invoiceId,
            @RequestBody AddChargeRequest request
    ) {
        if (!request.isValidData()) {
            return ResponseEntity.badRequest().build();
        }

        InvoiceResponse response = addChargeUseCase.addCharge(invoiceId, request);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }
} 