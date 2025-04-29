package br.com.faj.bank.wallet;

import br.com.faj.bank.wallet.domain.RegisterPaymentMethodUseCase;
import br.com.faj.bank.wallet.model.RegisterPaymentRequest;
import br.com.faj.bank.wallet.model.domain.RegisterPaymentParamDomain;
import net.minidev.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/v1/wallet")
public class WalletController {

    private RegisterPaymentMethodUseCase registerUseCase;

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

    @PostMapping("/register-payment-method")
    public ResponseEntity<?> registerPaymentMethod(
            @RequestParam RegisterPaymentRequest paymentMethod
    ) {

        if (!paymentMethod.isValidData()) {
            var json = new JSONObject();
            json.put("message", "All fields are required");
            return ResponseEntity.badRequest().body(json.toJSONString());
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
                var json = new JSONObject();
                json.put("message", "Card registered successfully");
                json.put("cardId", result.cardId());
                return ResponseEntity.ok(json.toJSONString());
            }
            case ERROR, INVALID_DATA -> {
                var json = new JSONObject();
                json.put("message", "Invalid data");
                return ResponseEntity.badRequest().body(json.toJSONString());
            }
            case ALREADY_REGISTERED -> {
                var json = new JSONObject();
                json.put("message", "Card already registered");
                return ResponseEntity.badRequest().body(json.toJSONString());
            }
        }

        return ResponseEntity.badRequest().build();
    }

}
