package br.com.faj.bank.checkout;

import br.com.faj.bank.checkout.domain.CancelTransactionUseCase;
import br.com.faj.bank.checkout.domain.CreateTransactionUseCase;
import br.com.faj.bank.checkout.domain.GetTransactionUseCase;
import br.com.faj.bank.checkout.domain.PayTransactionUseCase;
import br.com.faj.bank.checkout.model.request.CreateTransactionRequest;
import br.com.faj.bank.checkout.model.response.CheckoutFailResponse;
import br.com.faj.bank.checkout.model.response.CheckoutTransactionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/v1/checkout")
public class CheckoutController {

    private final CreateTransactionUseCase createTransactionUseCase;
    private final GetTransactionUseCase getTransactionUseCase;
    private final CancelTransactionUseCase cancelTransactionUseCase;
    private final PayTransactionUseCase payTransactionUseCase;

    public CheckoutController(
            CreateTransactionUseCase createTransactionUseCase,
            CancelTransactionUseCase cancelTransactionUseCase,
            PayTransactionUseCase payTransactionUseCase,
            GetTransactionUseCase getTransactionUseCase
    ) {
        this.createTransactionUseCase = createTransactionUseCase;
        this.getTransactionUseCase = getTransactionUseCase;
        this.payTransactionUseCase = payTransactionUseCase;
        this.cancelTransactionUseCase = cancelTransactionUseCase;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTransaction(
            @RequestBody CreateTransactionRequest request
    ) {

        try {
            var transaction = createTransactionUseCase.create(
                    request.type(),
                    request.invoiceId(),
                    request.invoiceId()
            );

            var response = new CheckoutTransactionResponse(transaction.id(), transaction.status());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {

            var response = new CheckoutFailResponse(
                    e.getMessage(),
                    request.invoiceId()
            );

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/status/{transaction_id}")
    public ResponseEntity<?> getTransactionStatus(
            @PathVariable("transaction_id") Long transactionId
    ) {
        if (transactionId == null) {
            return ResponseEntity.badRequest().body(null);
        }

        try {
            var transaction = getTransactionUseCase.get(transactionId);
            // Precisa criar novas propriedades e salva na base os dados doo cartão + fatura para evitar query
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        } catch (RuntimeException e) {
            var response = new CheckoutFailResponse(
                    e.getMessage(),
                    transactionId
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/cancel/{transaction_id}")
    public ResponseEntity<?> cancelTransaction(
            @PathVariable("transaction_id") Long transactionId
    ) {
        try {
            cancelTransactionUseCase.cancel(transactionId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            var response = new CheckoutFailResponse(
                    e.getMessage(),
                    null
            );

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/confirm-payment/{transaction_id}")
    public ResponseEntity<?> confirmPayment(
            @PathVariable("transaction_id") Long transactionId
    ) {
        try {
            var transaction = payTransactionUseCase.pay(transactionId);
            var response = new HashMap<String, String>().put("status", transaction.name());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
