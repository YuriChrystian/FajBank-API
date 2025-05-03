package br.com.faj.bank.customer.domain;

import br.com.faj.bank.core.SessionCustomer;
import br.com.faj.bank.customer.data.CustomerRepository;
import br.com.faj.bank.customer.model.domain.CustomerDomain;
import org.springframework.stereotype.Component;

@Component
public class FetchCustomerUseCase {

    private final CustomerRepository customerRepository;
    private final SessionCustomer sessionCustomer;

    public FetchCustomerUseCase(
            CustomerRepository customerRepository,
            SessionCustomer sessionCustomer
    ) {
        this.customerRepository = customerRepository;
        this.sessionCustomer = sessionCustomer;
    }

    public CustomerDomain fetch() {
        var customer = customerRepository.findById(sessionCustomer.getCustomerId());

        if (customer.isEmpty()) {
            return null;
        }

        return  customer.map(c -> new CustomerDomain(c.getFirstName(), c.getLastName(), c.getEmail())).get();
    }
}
