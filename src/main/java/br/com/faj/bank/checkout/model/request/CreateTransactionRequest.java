package br.com.faj.bank.checkout.model.request;

import br.com.faj.bank.checkout.model.PaymentType;
import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateTransactionRequest(
        @JsonProperty("invoice_id") Long invoiceId,
        @JsonProperty("payment_method_id") Long paymentMethodId,
        @JsonProperty("payment_type") PaymentType type
) {
}
