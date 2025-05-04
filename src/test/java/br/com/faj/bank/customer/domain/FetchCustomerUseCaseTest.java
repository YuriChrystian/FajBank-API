package br.com.faj.bank.customer.domain;

import br.com.faj.bank.core.SessionCustomer;
import br.com.faj.bank.customer.data.CustomerRepository;
import br.com.faj.bank.customer.model.domain.CustomerDomain;
import br.com.faj.bank.customer.model.entity.CustomerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FetchCustomerUseCaseTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private SessionCustomer sessionCustomer;

    private FetchCustomerUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new FetchCustomerUseCase(customerRepository, sessionCustomer);
    }

    @Test
    void givenExistingCustomer_whenFetch_thenReturnCustomerDomain() {
        // Arrange
        Long customerId = 1L;
        CustomerEntity customer = new CustomerEntity();
        customer.setId(customerId);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john@example.com");

        when(sessionCustomer.getCustomerId()).thenReturn(customerId);
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        // Act
        CustomerDomain result = useCase.fetch();

        // Assert
        assertNotNull(result);
        assertEquals("John", result.firstName());
        assertEquals("Doe", result.lastName());
        assertEquals("john@example.com", result.email());
        verify(customerRepository).findById(customerId);
        verify(sessionCustomer).getCustomerId();
    }

    @Test
    void givenNonExistingCustomer_whenFetch_thenReturnNull() {
        // Arrange
        Long customerId = 1L;
        when(sessionCustomer.getCustomerId()).thenReturn(customerId);
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // Act
        CustomerDomain result = useCase.fetch();

        // Assert
        assertNull(result);
        verify(customerRepository).findById(customerId);
        verify(sessionCustomer).getCustomerId();
    }
} 