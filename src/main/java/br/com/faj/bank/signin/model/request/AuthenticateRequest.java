package br.com.faj.bank.signin.model.request;

public record AuthenticateRequest(
        String email,
        String password
) {

    public String getPasswordByQuery() {
        return "{noop}" + password;
    }

}
