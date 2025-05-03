package br.com.faj.bank.wallet.domain;

import br.com.faj.bank.core.SessionCustomer;
import br.com.faj.bank.wallet.data.CardPaymentMethodRepository;
import br.com.faj.bank.wallet.model.domain.RegisterPaymentParamDomain;
import br.com.faj.bank.wallet.model.domain.RegisterResultDomain;
import br.com.faj.bank.wallet.model.domain.RegisterResultType;
import br.com.faj.bank.wallet.model.entity.CardPaymentMethodEntity;
import org.springframework.stereotype.Component;

@Component
public class RegisterPaymentMethodUseCase {

    private CardPaymentMethodRepository repository;
    private SessionCustomer sessionCustomer;

    public RegisterPaymentMethodUseCase(
            CardPaymentMethodRepository repository,
            SessionCustomer sessionCustomer
    ) {
        this.repository = repository;
        this.sessionCustomer = sessionCustomer;
    }

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
                cardHolderName.split(" ").length < 2
        ) {
            return RegisterResultDomain.of(RegisterResultType.ERROR);
        }

        // verify number card already registered, if true, return ALREADY_REGISTERED
        // saved item in database here
        CardPaymentMethodEntity entity = new CardPaymentMethodEntity();
        entity.setCardNumber(number);
        entity.setCvv(cvv);
        entity.setExpirationDate(expirationDate);
        entity.setCardHolderName(cardHolderName);
        entity.setType("CREDIT");
        entity.setBrand("NONE");
        entity.setCustomerId(sessionCustomer.getCustomerId());

        CardPaymentMethodEntity card = repository.save(entity);

        return RegisterResultDomain.of(RegisterResultType.SUCCESS, card.getId().toString());
    }
}
