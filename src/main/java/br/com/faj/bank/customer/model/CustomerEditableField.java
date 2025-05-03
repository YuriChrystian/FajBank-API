package br.com.faj.bank.customer.model;

public enum CustomerEditableField {
    LAST_NAME,
    FIRST_NAME,
    EMAIL;

    public static CustomerEditableField fromString(String data) {
        return switch (data) {
            case "LAST_NAME" -> CustomerEditableField.LAST_NAME;
            case "FIRST_NAME" -> CustomerEditableField.FIRST_NAME;
            case "EMAIL" -> CustomerEditableField.EMAIL;
            default -> throw new IllegalArgumentException(data);
        };
    }

}
