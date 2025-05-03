package br.com.faj.bank.customer;

import br.com.faj.bank.customer.domain.FetchCustomerUseCase;
import br.com.faj.bank.customer.domain.UpdateCustomerFieldUseCase;
import br.com.faj.bank.customer.model.request.UpdateCustomerRequest;
import br.com.faj.bank.customer.model.response.CustomerResponse;
import br.com.faj.bank.timeline.domain.RegisterCustomerTimelineUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/customer")
public class CustomerController {

    private final FetchCustomerUseCase fetchCustomerUseCase;
    private final UpdateCustomerFieldUseCase updateCustomerFieldUseCase;
    private final RegisterCustomerTimelineUseCase registerCustomerTimelineUseCase;

    public CustomerController(
            RegisterCustomerTimelineUseCase registerCustomerTimelineUseCase,
            UpdateCustomerFieldUseCase updateCustomerFieldUseCase,
            FetchCustomerUseCase fetchCustomerUseCase
    ) {
        this.registerCustomerTimelineUseCase = registerCustomerTimelineUseCase;
        this.updateCustomerFieldUseCase = updateCustomerFieldUseCase;
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

    @PostMapping
    public ResponseEntity<CustomerResponse> editField(
            @RequestBody UpdateCustomerRequest request
    ) {

        var data = updateCustomerFieldUseCase.update(request.fields());

        if (data == null) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        registerCustomerTimelineUseCase.registerUpdateProfile();

        return CustomerResponse.responseOk(data);
    }
}
