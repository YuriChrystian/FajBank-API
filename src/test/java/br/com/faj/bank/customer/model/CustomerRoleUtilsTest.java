package br.com.faj.bank.customer.model;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class CustomerRoleUtilsTest {

    @Test
    void givenAdminRole_whenGetByRole_thenReturnAdminRoleWithAuthorities() {
        // Act
        CustomerRole role = CustomerRoleUtils.getByRole("a113");

        // Assert
        assertEquals(CustomerRole.ADMIN, role);
        Collection<? extends GrantedAuthority> authorities = role.getAuthoritiesByRole();
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_CUSTOMER")));
        assertEquals(2, authorities.size());
    }

    @Test
    void givenCustomerRole_whenGetByRole_thenReturnCustomerRoleWithAuthority() {
        // Act
        CustomerRole role = CustomerRoleUtils.getByRole("c113");

        // Assert
        assertEquals(CustomerRole.CUSTOMER, role);
        Collection<? extends GrantedAuthority> authorities = role.getAuthoritiesByRole();
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_CUSTOMER")));
        assertEquals(1, authorities.size());
    }

    @Test
    void givenInvalidRole_whenGetByRole_thenReturnNull() {
        // Act
        CustomerRole role = CustomerRoleUtils.getByRole("invalid");

        // Assert
        assertNull(role);
    }
} 