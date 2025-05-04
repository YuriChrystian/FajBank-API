package br.com.faj.bank.customer.domain;

import br.com.faj.bank.core.SessionCustomer;
import br.com.faj.bank.customer.data.CustomerRepository;
import br.com.faj.bank.customer.model.CustomerEditableField;
import br.com.faj.bank.customer.model.domain.CustomerDomain;
import br.com.faj.bank.customer.model.entity.CustomerEntity;
import br.com.faj.bank.customer.model.request.FieldEditDataRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UpdateCustomerFieldUseCaseTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private SessionCustomer sessionCustomer;

    private UpdateCustomerFieldUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new UpdateCustomerFieldUseCase(customerRepository, sessionCustomer);
    }

    @Test
    void givenExistingCustomerAndValidFields_whenUpdate_thenReturnUpdatedCustomer() {
        // Arrange
        Long customerId = 1L;
        CustomerEntity customer = new CustomerEntity();
        customer.setId(customerId);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john@example.com");

        List<FieldEditDataRequest<String>> fields = List.of(
            new FieldEditDataRequest<>("FIRST_NAME", "Jane"),
            new FieldEditDataRequest<>("LAST_NAME", "Smith"),
            new FieldEditDataRequest<>("EMAIL", "jane@example.com")
        );

        when(sessionCustomer.getCustomerId()).thenReturn(customerId);
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(CustomerEntity.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        CustomerDomain result = useCase.update(fields);

        // Assert
        assertNotNull(result);
        assertEquals("Jane", result.firstName());
        assertEquals("Smith", result.lastName());
        assertEquals("jane@example.com", result.email());
        verify(customerRepository).findById(customerId);
        verify(customerRepository).save(any(CustomerEntity.class));
    }

    @Test
    void givenNonExistingCustomer_whenUpdate_thenReturnNull() {
        // Arrange
        Long customerId = 1L;
        List<FieldEditDataRequest<String>> fields = List.of(
            new FieldEditDataRequest<>("FIRST_NAME", "Jane")
        );

        when(sessionCustomer.getCustomerId()).thenReturn(customerId);
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // Act
        CustomerDomain result = useCase.update(fields);

        // Assert
        assertNull(result);
        verify(customerRepository).findById(customerId);
        verify(customerRepository, never()).save(any(CustomerEntity.class));
    }

    @Test
    void givenInvalidFieldType_whenUpdate_thenThrowIllegalArgumentException() {
        // Arrange
        Long customerId = 1L;
        CustomerEntity customer = new CustomerEntity();
        customer.setId(customerId);

        List<FieldEditDataRequest<String>> fields = List.of(
            new FieldEditDataRequest<>("INVALID_FIELD", "value")
        );

        when(sessionCustomer.getCustomerId()).thenReturn(customerId);
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> useCase.update(fields));
        verify(customerRepository).findById(customerId);
        verify(customerRepository, never()).save(any(CustomerEntity.class));
    }
} 