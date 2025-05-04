package br.com.faj.bank.invoice.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record InvoiceResponse(
    @JsonProperty("id") Long id,
    @JsonProperty("due_date") LocalDate dueDate,
    @JsonProperty("total_amount") BigDecimal totalAmount,
    @JsonProperty("status") String status,
    @JsonProperty("charges") List<InvoiceChargeResponse> charges
) {} 