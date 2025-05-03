package br.com.faj.bank.customer.model.request;

import java.util.List;


public record UpdateCustomerRequest(
        List<FieldEditDataRequest<String>> fields
) {
}
