package br.com.faj.bank.invoice.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateInvoiceRequest(
    @JsonProperty("due_date") LocalDateTime dueDate,
    @JsonProperty("description") String description,
    @JsonProperty("amount") BigDecimal amount
) {
    public boolean isValidData() {
        return dueDate != null && description != null && amount != null;
    }
} 