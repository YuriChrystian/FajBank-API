package br.com.faj.bank.signin.model.response;

import java.time.LocalDateTime;

public record AuthenticateResponse(
        String token,
        LocalDateTime time
) {
}
