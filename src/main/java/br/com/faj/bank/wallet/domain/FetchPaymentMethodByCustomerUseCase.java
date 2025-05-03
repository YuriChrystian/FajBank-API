package br.com.faj.bank.wallet.domain;

import br.com.faj.bank.core.AppHelper;
import br.com.faj.bank.core.SessionCustomer;
import br.com.faj.bank.wallet.data.CardPaymentMethodRepository;
import br.com.faj.bank.wallet.domain.converte.MobilePaymentMethodFormatConverter;
import br.com.faj.bank.wallet.model.entity.CardPaymentMethodEntity;
import br.com.faj.bank.wallet.model.response.PaymentMethodResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class FetchPaymentMethodByCustomerUseCase {

    private CardPaymentMethodRepository repository;
    private SessionCustomer sessionCustomer;

    public FetchPaymentMethodByCustomerUseCase(
            CardPaymentMethodRepository repository,
            SessionCustomer sessionCustomer
    ) {
        this.repository = repository;
        this.sessionCustomer = sessionCustomer;
    }

    public List<PaymentMethodResponse> fetch() {
        Long customerId = sessionCustomer.getCustomerId();
        List<CardPaymentMethodEntity> list = repository.findAllByCustomerId(customerId);

        if (list.isEmpty()) {
            return Collections.emptyList();
        }

        return list.stream().map(MobilePaymentMethodFormatConverter::converter).toList();
    }

}
