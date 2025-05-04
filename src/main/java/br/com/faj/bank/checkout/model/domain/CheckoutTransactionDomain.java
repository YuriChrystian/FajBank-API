package br.com.faj.bank.checkout.model.domain;

import br.com.faj.bank.checkout.model.TransactionStatus;
import br.com.faj.bank.checkout.model.entity.CheckoutTransactionEntity;

import java.time.LocalDateTime;

public record CheckoutTransactionDomain(
        Long id,
        Long invoiceId,
        Long paymentMethodId,
        Long customerId,
        TransactionStatus status,
        LocalDateTime created,
        LocalDateTime updated
) {

    public static CheckoutTransactionDomain of(CheckoutTransactionEntity entity) {
       return new CheckoutTransactionDomain(
               entity.getId(),
               entity.getInvoiceId(),
               entity.getPaymentMethodId(),
               entity.getCustomerId(),
               entity.getStatus(),
               entity.getCreated(),
               entity.getUpdated()
       );
    }
}
