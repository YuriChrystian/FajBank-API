package br.com.faj.bank.customer.model.request;

public record FieldEditDataRequest<T>(
        String type,
        T description
) { }
