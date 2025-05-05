package br.com.faj.bank.checkout.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CheckoutFailResponse(
       @JsonProperty("message") String message,
       @JsonProperty("invoice_id") Long invoiceId
) {
}
