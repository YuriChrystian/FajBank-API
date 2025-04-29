package br.com.faj.bank.wallet.model.domain;

public enum RegisterResultType {
    SUCCESS("Success"),
    INVALID_DATA("Invalid data"),
    ALREADY_REGISTERED("Already registered"),
    ERROR("Error");

    private final String description;

    RegisterResultType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
