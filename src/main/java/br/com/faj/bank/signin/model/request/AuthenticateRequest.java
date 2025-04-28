package br.com.faj.bank.signin.model.request;

public record AuthenticateRequest(
        String email,
        String password
) {
}
