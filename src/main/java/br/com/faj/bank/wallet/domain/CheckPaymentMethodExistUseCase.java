package br.com.faj.bank.wallet.domain;

import br.com.faj.bank.core.SessionCustomer;
import br.com.faj.bank.wallet.data.CardPaymentMethodRepository;
import br.com.faj.bank.wallet.model.entity.CardPaymentMethodEntity;
import org.springframework.stereotype.Component;

@Component
public class CheckPaymentMethodExistUseCase {

    private final CardPaymentMethodRepository repository;
    private final SessionCustomer session;

    public CheckPaymentMethodExistUseCase(
            CardPaymentMethodRepository repository,
            SessionCustomer session
    ) {
        this.repository = repository;
        this.session = session;
    }


    public boolean existsPaymentMethod(String cardNumber) {
        return repository.findPaymentMethodByNumberAndCustomer(cardNumber, session.getCustomerId()) != null;
    }

    public CardPaymentMethodEntity getPaymentMethodById(Long id) {
        return repository.findPaymentMethodByIdAndCustomer(id, session.getCustomerId());
    }

}
