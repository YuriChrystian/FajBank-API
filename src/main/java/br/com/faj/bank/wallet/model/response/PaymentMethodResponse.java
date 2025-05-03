package br.com.faj.bank.wallet.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PaymentMethodResponse(
        @JsonProperty("id") Long id,
        @JsonProperty("lastDigits") String cardNumber,
        @JsonProperty("holder_name") String cardHolderName,
        @JsonProperty("expiration") String expirationDate,
        @JsonProperty("brand") String brand,
        @JsonProperty("type") String type
) {
}
