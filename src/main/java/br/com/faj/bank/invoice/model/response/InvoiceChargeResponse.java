package br.com.faj.bank.invoice.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record InvoiceChargeResponse(
    @JsonProperty("id") Long id,
    @JsonProperty("description") String description,
    @JsonProperty("amount") BigDecimal amount,
    @JsonProperty("charge_date") LocalDateTime chargeDate
) {} 