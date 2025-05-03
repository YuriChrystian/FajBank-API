package br.com.faj.bank.wallet.domain;

import br.com.faj.bank.wallet.data.CardPaymentMethodRepository;
import org.springframework.stereotype.Component;

@Component
public class RemovePaymentMethodUseCase {

    private final CardPaymentMethodRepository repository;

    public RemovePaymentMethodUseCase(
            CardPaymentMethodRepository repository
    ) {
        this.repository = repository;
    }

    public Boolean remove(Long cardId) {

        if (!repository.existsById(cardId)) {
            return null;
        }

        repository.deleteById(cardId);
        return true;
    }
}
