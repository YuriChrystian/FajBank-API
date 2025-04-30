package br.com.faj.bank.wallet;

import br.com.faj.bank.wallet.domain.RegisterPaymentMethodUseCase;
import br.com.faj.bank.wallet.model.RegisterPaymentRequest;
import br.com.faj.bank.wallet.model.RegisterPaymentResponse;
import br.com.faj.bank.wallet.model.domain.RegisterPaymentParamDomain;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/wallet")
public class WalletController {

    private final RegisterPaymentMethodUseCase registerUseCase;

    public WalletController(
            RegisterPaymentMethodUseCase registerUseCase
    ) {
        this.registerUseCase = registerUseCase;
    }

    @GetMapping("/card-bin")
    public ResponseEntity<String> recoveryBin(
            @RequestParam("bin") String bin
    ) {
        // recovery bin list here
        return ResponseEntity.ok().build();
    }

    @PostMapping(
            path = "/register-payment-method",
            consumes = "application/json"
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RegisterPaymentResponse> registerPaymentMethod(
            @RequestBody RegisterPaymentRequest paymentMethod
    ) {

        if (!paymentMethod.isValidData()) {
            return RegisterPaymentResponse.badRequest("All fields are required");
        }

        RegisterPaymentParamDomain domain = new RegisterPaymentParamDomain(
                paymentMethod.cardNumber(),
                paymentMethod.cardHolderName(),
                paymentMethod.expirationDate(),
                paymentMethod.cvv()
        );

        var result = registerUseCase.registerPaymentMethod(domain);

        switch (result.status()) {
            case SUCCESS -> {
                return RegisterPaymentResponse.created("Card registered successfully", result.cardId());
            }
            case ERROR, INVALID_DATA -> {
                return RegisterPaymentResponse.badRequest("Invalid data");
            }
            case ALREADY_REGISTERED -> {
                return RegisterPaymentResponse.badRequest("Card already registered");
            }
        }

        return ResponseEntity.badRequest().build();
    }
}
