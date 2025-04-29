package br.com.faj.bank.wallet.model.domain;

public record RegisterResultDomain(
        RegisterResultType status,
        String cardId
) {

    public static RegisterResultDomain of(RegisterResultType status, String cardId) {
        return new RegisterResultDomain(status, cardId);
    }

    public static RegisterResultDomain of(RegisterResultType status) {
        return new RegisterResultDomain(status, null);
    }
}
