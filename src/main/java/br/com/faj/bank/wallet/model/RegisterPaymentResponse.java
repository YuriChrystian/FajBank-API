package br.com.faj.bank.wallet.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RegisterPaymentResponse(
        String message,
        String cardId
) {

    public static ResponseEntity<RegisterPaymentResponse> response(
            String message,
            String cardId,
            HttpStatus status
    ) {
        return new ResponseEntity<>(
                new RegisterPaymentResponse(
                        message,
                        cardId
                ),
                status
        );
    }

    public static ResponseEntity<RegisterPaymentResponse> badRequest(String message) {
        return response(
                message,
                null,
                HttpStatus.BAD_REQUEST
        );
    }

    public static ResponseEntity<RegisterPaymentResponse> created(String message, String cardId) {
        return response(
                message,
                cardId,
                HttpStatus.CREATED
        );
    }
}
