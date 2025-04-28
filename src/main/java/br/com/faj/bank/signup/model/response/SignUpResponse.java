package br.com.faj.bank.signup.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SignUpResponse implements SignUpStrategy {

    @JsonProperty("message")
    private String message;

    @JsonProperty("status")
    private String status;

    public SignUpResponse(String message, String status) {
        this.message = message;
        this.status = status;
    }
}
