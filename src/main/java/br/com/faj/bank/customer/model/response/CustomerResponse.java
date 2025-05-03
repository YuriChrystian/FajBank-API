package br.com.faj.bank.customer.model.response;

import br.com.faj.bank.customer.model.CustomerEditableField;
import br.com.faj.bank.customer.model.domain.CustomerDomain;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

public record CustomerResponse(
        @JsonProperty("first_name") String firstName,
        @JsonProperty("last_name") String lastName,
        @JsonProperty("email") String email,
        @JsonProperty("editable") List<CustomerEditableField> editable
) {

    public static ResponseEntity<CustomerResponse> responseOk(
            CustomerDomain domain
    ) {
        return new ResponseEntity<>(
                new CustomerResponse(
                        domain.firstName(),
                        domain.lastName(),
                        domain.email(),
                        Arrays.stream(CustomerEditableField.values()).toList()
                ),
                HttpStatus.OK
        );
    }
}
