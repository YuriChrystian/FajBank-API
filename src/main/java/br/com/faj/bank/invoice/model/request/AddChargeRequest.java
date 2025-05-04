package br.com.faj.bank.invoice.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AddChargeRequest(
    @JsonProperty("description") String description,
    @JsonProperty("amount") String amount
) {
    public boolean isValidData() {
        return description != null && amount != null;
    }
} 