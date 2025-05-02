package br.com.faj.bank.wallet;

import br.com.faj.bank.AppHelper;
import br.com.faj.bank.wallet.data.CardPaymentMethodRepository;
import br.com.faj.bank.wallet.domain.RegisterPaymentMethodUseCase;
import br.com.faj.bank.wallet.domain.converte.MobilePaymentMethodFormatConverter;
import br.com.faj.bank.wallet.model.entity.CardPaymentMethodEntity;
import br.com.faj.bank.wallet.model.request.RegisterPaymentRequest;
import br.com.faj.bank.wallet.model.response.PaymentMethodResponse;
import br.com.faj.bank.wallet.model.response.RegisterPaymentResponse;
import br.com.faj.bank.wallet.model.domain.RegisterPaymentParamDomain;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/v1/wallet")
public class WalletController {

    private final RegisterPaymentMethodUseCase registerUseCase;
    private final CardPaymentMethodRepository repository;

    public WalletController(
            RegisterPaymentMethodUseCase registerUseCase,
            CardPaymentMethodRepository repository
    ) {
        this.registerUseCase = registerUseCase;
        this.repository = repository;
    }

    @GetMapping("/card-bin")
    public ResponseEntity<String> recoveryBin(
            @RequestParam("bin") String bin
    ) {
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<PaymentMethodResponse>> allPaymentMethods() {

        Long customerId = AppHelper.getCustomer().getId();
        List<CardPaymentMethodEntity> list = repository.findAllByCustomerId(customerId);

        if (list.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        var cards = list.stream().map(MobilePaymentMethodFormatConverter::converter);

        return ResponseEntity.ok(cards.toList());
    }

    // TODO criar regra para nao deixar cadastrar um cartao ja existente
    // Travar o limite de cartao em 5
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
