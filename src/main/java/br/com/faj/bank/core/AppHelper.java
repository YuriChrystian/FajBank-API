package br.com.faj.bank.core;

import br.com.faj.bank.customer.model.entity.CustomerEntity;
import org.springframework.security.core.context.SecurityContextHolder;

public class AppHelper {

    public static CustomerEntity getCustomer() {
        CustomerEntity customer = null;
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            customer = ((CustomerEntity) authentication.getPrincipal());
        }
        return customer;
    }
}
