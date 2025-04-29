package br.com.faj.bank.wallet.domain;

import br.com.faj.bank.wallet.model.domain.RegisterPaymentParamDomain;
import br.com.faj.bank.wallet.model.domain.RegisterResultDomain;
import br.com.faj.bank.wallet.model.domain.RegisterResultType;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegisterPaymentMethodUseCase {

    public RegisterResultDomain registerPaymentMethod(
            RegisterPaymentParamDomain domain
    ) {
        String number = domain.cardNumber().replace(" ", "");
        String cvv = domain.cvv().trim();
        String expirationDate = domain.expirationDate().trim();
        String cardHolderName = domain.cardHolderName();

        if (number.length() != 11 ||
                cvv.length() != 3 ||
                expirationDate.length() != 5 ||
                cardHolderName.split(" ").length > 1
        ) {
            return RegisterResultDomain.of(RegisterResultType.ERROR);
        }

        // saved item in database here

        return RegisterResultDomain.of(RegisterResultType.SUCCESS, UUID.randomUUID().toString());
    }

}
