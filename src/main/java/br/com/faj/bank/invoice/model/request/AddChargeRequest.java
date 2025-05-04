package br.com.faj.bank.invoice.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record AddChargeRequest(
    @JsonProperty("description") String description,
    @JsonProperty("amount") BigDecimal amount
) {
    public boolean isValidData() {
        return description != null && amount != null;
    }
} 