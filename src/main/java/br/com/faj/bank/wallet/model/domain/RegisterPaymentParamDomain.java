package br.com.faj.bank.wallet.model.domain;

public record RegisterPaymentParamDomain(
        String cardNumber,
        String cardHolderName,
        String expirationDate,
        String cvv
) {
}
