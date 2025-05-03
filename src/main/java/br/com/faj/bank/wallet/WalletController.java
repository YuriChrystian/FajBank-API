package br.com.faj.bank.wallet;

import br.com.faj.bank.core.SessionCustomer;
import br.com.faj.bank.wallet.data.CardPaymentMethodRepository;
import br.com.faj.bank.wallet.data.WalletCustomerRepository;
import br.com.faj.bank.wallet.domain.CheckPaymentMethodExistUseCase;
import br.com.faj.bank.wallet.domain.FetchPaymentMethodByCustomerUseCase;
import br.com.faj.bank.wallet.domain.RegisterPaymentMethodUseCase;
import br.com.faj.bank.wallet.domain.RemovePaymentMethodUseCase;
import br.com.faj.bank.wallet.model.entity.WalletCustomerEntity;
import br.com.faj.bank.wallet.model.request.RegisterPaymentRequest;
import br.com.faj.bank.wallet.model.response.PaymentMethodResponse;
import br.com.faj.bank.wallet.model.response.RegisterPaymentResponse;
import br.com.faj.bank.wallet.model.domain.RegisterPaymentParamDomain;
import br.com.faj.bank.wallet.model.response.WalletCustomerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/v1/wallet")
public class WalletController {

    private final RemovePaymentMethodUseCase removePaymentMethodUseCase;
    private final CheckPaymentMethodExistUseCase checkExistUseCase;
    private final RegisterPaymentMethodUseCase registerUseCase;
    private final FetchPaymentMethodByCustomerUseCase fetchPaymentMethodUseCase;

    // remover no futuro, colocar dentro de uma use case
    private final WalletCustomerRepository walletCustomerRepository;

    private final SessionCustomer sessionCustomer;

    public WalletController(
            RegisterPaymentMethodUseCase registerUseCase,
            CheckPaymentMethodExistUseCase checkExistUseCase,
            RemovePaymentMethodUseCase removePaymentMethodUseCase,
            FetchPaymentMethodByCustomerUseCase fetchPaymentMethodUseCase,
            WalletCustomerRepository walletCustomerRepository,
            SessionCustomer sessionCustomer

    ) {
        this.walletCustomerRepository = walletCustomerRepository;
        this.registerUseCase = registerUseCase;
        this.checkExistUseCase = checkExistUseCase;
        this.removePaymentMethodUseCase = removePaymentMethodUseCase;
        this.sessionCustomer = sessionCustomer;
        this.fetchPaymentMethodUseCase = fetchPaymentMethodUseCase;
    }

    @GetMapping("/bff-mobile")
    public ResponseEntity<WalletCustomerResponse> getBalance() {

        var wallet = walletCustomerRepository.findByCustomerId(sessionCustomer.getCustomerId());

        // rever logica na criacao do consumidor
        if (wallet == null) {
            WalletCustomerEntity walletCustomerEntity = new WalletCustomerEntity();
            walletCustomerEntity.setCustomerId(sessionCustomer.getCustomerId());
            walletCustomerEntity.setBalance(BigDecimal.ZERO);

            walletCustomerRepository.save(walletCustomerEntity);
            wallet = walletCustomerEntity;
        }

        return ResponseEntity.ok(new WalletCustomerResponse(
                        wallet.getBalance(),
                        fetchPaymentMethodUseCase.fetch()
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<PaymentMethodResponse>> allPaymentMethods() {
        return ResponseEntity.ok(fetchPaymentMethodUseCase.fetch());
    }

    @PostMapping(
            path ="/remove/{cardId}",
            consumes = "application/json"
    )
    public ResponseEntity<?> removePaymentMethod(
            @PathVariable("cardId") Long cardId) {

        var remove = removePaymentMethodUseCase.remove(cardId);

        if (remove == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

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

        if (checkExistUseCase.existsPaymentMethod(paymentMethod.cardNumber())) {
            return RegisterPaymentResponse.badRequest("Card already registered");
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
