package br.com.faj.bank.signin.model.response;

import java.util.Date;

public record AuthenticateResponse(
        String token,
        Date time
) {
}
