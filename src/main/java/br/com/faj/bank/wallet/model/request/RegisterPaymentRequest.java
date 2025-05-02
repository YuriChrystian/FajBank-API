package br.com.faj.bank.wallet.model.request;

public record RegisterPaymentRequest(
        String cardNumber,
        String cardHolderName,
        String expirationDate,
        String cvv
) {

    public boolean isValidData() {
        return cardNumber != null && cardHolderName != null && expirationDate != null && cvv != null;
    }

}
