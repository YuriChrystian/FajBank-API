package br.com.faj.bank.timeline.model;

public enum TimelineInvoiceType {
    INVOICE_CREATED("Nova fatura criada"),
    CHARGE_ADDED("Nova cobrança adicionada");

    private final String description;

    TimelineInvoiceType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 