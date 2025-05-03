package br.com.faj.bank.customer;

import br.com.faj.bank.customer.domain.FetchCustomerUseCase;
import br.com.faj.bank.customer.model.response.CustomerResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/customer")
public class CustomerController {

    private final FetchCustomerUseCase fetchCustomerUseCase;

    public CustomerController(
            FetchCustomerUseCase fetchCustomerUseCase
    ) {
        this.fetchCustomerUseCase = fetchCustomerUseCase;
    }

    @GetMapping
    public ResponseEntity<CustomerResponse> fetchCustomer() {

        var data = fetchCustomerUseCase.fetch();

        if (data == null) {
            throw new SessionAuthenticationException("No customer found");
        }

        return CustomerResponse.responseOk(data);
    }

}
