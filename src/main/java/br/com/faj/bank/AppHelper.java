package br.com.faj.bank;

import br.com.faj.bank.signup.model.entity.CustomerEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

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
