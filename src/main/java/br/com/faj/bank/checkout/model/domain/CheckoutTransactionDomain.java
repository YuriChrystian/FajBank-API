package br.com.faj.bank.checkout.model.domain;

import br.com.faj.bank.checkout.model.TransactionStatus;
import br.com.faj.bank.checkout.model.entity.CheckoutTransactionEntity;
import br.com.faj.bank.invoice.model.entity.InvoiceEntity;
import br.com.faj.bank.wallet.model.entity.CardPaymentMethodEntity;

import java.time.LocalDateTime;
import java.util.HashMap;

public record CheckoutTransactionDomain(
        Long id,
        Long invoiceId,
        Long paymentMethodId,
        Long customerId,
        TransactionStatus status,
        LocalDateTime created,
        LocalDateTime updated,
        HashMap<String, String> metadataInvoice,
        HashMap<String, String> paymentMethod
) {

    public static CheckoutTransactionDomain of(CheckoutTransactionEntity entity) {
        return new CheckoutTransactionDomain(
                entity.getId(),
                entity.getInvoiceId(),
                entity.getPaymentMethodId(),
                entity.getCustomerId(),
                entity.getStatus(),
                entity.getCreated(),
                entity.getUpdated(),
                null,
                null
        );
    }

    public static CheckoutTransactionDomain of(
            CheckoutTransactionEntity entity,
            InvoiceEntity invoiceEntity,
            CardPaymentMethodEntity cardEntity
    ) {
        return new CheckoutTransactionDomain(
                entity.getId(),
                entity.getInvoiceId(),
                entity.getPaymentMethodId(),
                entity.getCustomerId(),
                entity.getStatus(),
                entity.getCreated(),
                entity.getUpdated(),
                invoiceEntity.getMetadata(),
                cardEntity.getMetadata()
        );
    }
}
