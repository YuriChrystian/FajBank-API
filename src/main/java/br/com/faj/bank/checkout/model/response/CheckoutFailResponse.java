package br.com.faj.bank.checkout.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CheckoutFailResponse(
       @JsonProperty("message") String message,
       @JsonProperty("invoice_id") Long invoiceId
) {
}
