package br.com.faj.bank.checkout;

import br.com.faj.bank.checkout.domain.CreateTransactionUseCase;
import br.com.faj.bank.checkout.model.request.CreateTransactionRequest;
import br.com.faj.bank.checkout.model.response.CheckoutFailResponse;
import br.com.faj.bank.checkout.model.response.CheckoutTransactionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/checkout")
public class CheckoutController {

    private final CreateTransactionUseCase createTransactionUseCase;

    public CheckoutController(
            CreateTransactionUseCase createTransactionUseCase
    ) {
        this.createTransactionUseCase = createTransactionUseCase;
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
            @PathVariable("transaction_id") String transactionId
    ) {
        // TODO
        return ResponseEntity.internalServerError().body(null);
    }

    @PutMapping("/cancel/{transaction_id}")
    public ResponseEntity<?> cancelTransaction(
            @PathVariable("transaction_id") String transactionId
    ) {
        // TODO
        return ResponseEntity.internalServerError().body(null);
    }
}
