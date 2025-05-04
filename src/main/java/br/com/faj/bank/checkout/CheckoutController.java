package br.com.faj.bank.checkout;

import br.com.faj.bank.checkout.domain.CreateTransactionUseCase;
import br.com.faj.bank.checkout.model.response.CheckoutTransactionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<CheckoutTransactionResponse> createTransaction() {



        return ResponseEntity.badRequest().body(null);
    }

}
