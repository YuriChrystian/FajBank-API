package br.com.faj.bank.customer.domain;

import br.com.faj.bank.core.SessionCustomer;
import br.com.faj.bank.customer.data.CustomerRepository;
import br.com.faj.bank.customer.model.CustomerEditableField;
import br.com.faj.bank.customer.model.domain.CustomerDomain;
import br.com.faj.bank.customer.model.request.FieldEditDataRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UpdateCustomerFieldUseCase {

    private final CustomerRepository customerRepository;
    private final SessionCustomer sessionCustomer;

    public UpdateCustomerFieldUseCase(
            CustomerRepository customerRepository,
            SessionCustomer sessionCustomer
    ) {
        this.customerRepository = customerRepository;
        this.sessionCustomer = sessionCustomer;
    }

    public CustomerDomain update(
            List<FieldEditDataRequest<String>> fields
    ) {

        var data = customerRepository.findById(sessionCustomer.getCustomerId());

        if (data.isEmpty()) {
            return null;
        }

        var customer = data.get();

        for (var field : fields) {
            switch (CustomerEditableField.fromString(field.type())) {
                case FIRST_NAME -> customer.setFirstName(field.description());
                case LAST_NAME -> customer.setLastName(field.description());
                case EMAIL -> customer.setEmail(field.description());
            }
        }

        var entity = customerRepository.save(customer);

        return new CustomerDomain(
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail()
        );
    }
}
