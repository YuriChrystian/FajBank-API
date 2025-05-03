package br.com.faj.bank.wallet.domain.converte;

import br.com.faj.bank.wallet.model.entity.CardPaymentMethodEntity;
import br.com.faj.bank.wallet.model.response.PaymentMethodResponse;

public class MobilePaymentMethodFormatConverter {

    public static PaymentMethodResponse converter(
            CardPaymentMethodEntity entity
    ) {
        int last = entity.getCardNumber().length();
        int begin = last - 4;

        String numberFormatted = entity.getCardNumber().substring(begin, last);

        return new PaymentMethodResponse(
                entity.getId(),
                numberFormatted,
                entity.getCardHolderName(),
                entity.getExpirationDate(),
                entity.getBrand(),
                entity.getType()

        );
    }
}
