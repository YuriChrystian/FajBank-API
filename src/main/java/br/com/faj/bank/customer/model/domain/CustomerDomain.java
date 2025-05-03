package br.com.faj.bank.customer.model.domain;

public record CustomerDomain(
        String firstName,
        String lastName,
        String email
) {
}
