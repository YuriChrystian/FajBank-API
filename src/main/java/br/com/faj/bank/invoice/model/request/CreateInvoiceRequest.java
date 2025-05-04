package br.com.faj.bank.invoice.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

public record CreateInvoiceRequest(
    @JsonProperty("due_date") LocalDate dueDate,
    @JsonProperty("description") String description,
    @JsonProperty("amount") String amount
) {
    public boolean isValidData() {
        return dueDate != null && description != null && amount != null;
    }
} 