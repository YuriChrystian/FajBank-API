package br.com.faj.bank.customer.model;

import java.util.Arrays;

public class CustomerRoleUtils {

    public static  CustomerRole getByRole(String role) {
        return Arrays.stream(CustomerRole.values()).filter(
                e -> e.getRole().equals(role)
        ).findFirst().orElse(null);
    }
}
