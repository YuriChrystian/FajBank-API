package br.com.faj.bank.checkout.model.response;

import br.com.faj.bank.checkout.model.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

public record CheckoutTransactionResponse(
        @JsonProperty("transaction_id") Long id,
        @JsonProperty("status") TransactionStatus status
) {
}
