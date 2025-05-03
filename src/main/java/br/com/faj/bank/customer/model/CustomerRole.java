package br.com.faj.bank.customer.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

public enum CustomerRole {

    ADMIN("a113"),
    CUSTOMER("c113");

    private String role;

    CustomerRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public Collection<? extends GrantedAuthority> getAuthoritiesByRole() {
        return switch (getRole()) {
            case "c113" -> List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
            case "a113" -> List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_CUSTOMER")
            );
            default -> List.of();
        };
    }
}
