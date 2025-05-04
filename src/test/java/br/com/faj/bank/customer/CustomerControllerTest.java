package br.com.faj.bank.customer;

import br.com.faj.bank.customer.domain.FetchCustomerUseCase;
import br.com.faj.bank.customer.domain.UpdateCustomerFieldUseCase;
import br.com.faj.bank.customer.model.domain.CustomerDomain;
import br.com.faj.bank.customer.model.request.FieldEditDataRequest;
import br.com.faj.bank.customer.model.request.UpdateCustomerRequest;
import br.com.faj.bank.customer.model.response.CustomerResponse;
import br.com.faj.bank.timeline.domain.RegisterCustomerTimelineUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

    @Mock
    private FetchCustomerUseCase fetchCustomerUseCase;

    @Mock
    private UpdateCustomerFieldUseCase updateCustomerFieldUseCase;

    @Mock
    private RegisterCustomerTimelineUseCase registerCustomerTimelineUseCase;

    private CustomerController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new CustomerController(
            registerCustomerTimelineUseCase,
            updateCustomerFieldUseCase,
            fetchCustomerUseCase
        );
    }

    @Test
    void givenExistingCustomer_whenFetchCustomer_thenReturnCustomerResponse() {
        // Arrange
        CustomerDomain customer = new CustomerDomain("John", "Doe", "john@example.com");
        when(fetchCustomerUseCase.fetch()).thenReturn(customer);

        // Act
        ResponseEntity<CustomerResponse> response = controller.fetchCustomer();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John", response.getBody().firstName());
        assertEquals("Doe", response.getBody().lastName());
        assertEquals("john@example.com", response.getBody().email());
        assertNotNull(response.getBody().editable());
        verify(fetchCustomerUseCase).fetch();
    }

    @Test
    void givenNonExistingCustomer_whenFetchCustomer_thenThrowException() {
        // Arrange
        when(fetchCustomerUseCase.fetch()).thenReturn(null);

        // Act & Assert
        assertThrows(SessionAuthenticationException.class, () -> controller.fetchCustomer());
        verify(fetchCustomerUseCase).fetch();
    }

    @Test
    void givenValidUpdateRequest_whenEditField_thenReturnUpdatedCustomer() {
        // Arrange
        List<FieldEditDataRequest<String>> fields = List.of(
            new FieldEditDataRequest<>("FIRST_NAME", "Jane")
        );
        UpdateCustomerRequest request = new UpdateCustomerRequest(fields);
        CustomerDomain updatedCustomer = new CustomerDomain("Jane", "Doe", "jane@example.com");

        when(updateCustomerFieldUseCase.update(fields)).thenReturn(updatedCustomer);
        doNothing().when(registerCustomerTimelineUseCase).registerUpdateProfile();

        // Act
        ResponseEntity<CustomerResponse> response = controller.editField(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Jane", response.getBody().firstName());
        assertEquals("Doe", response.getBody().lastName());
        assertEquals("jane@example.com", response.getBody().email());
        verify(updateCustomerFieldUseCase).update(fields);
        verify(registerCustomerTimelineUseCase).registerUpdateProfile();
    }

    @Test
    void givenInvalidUpdateRequest_whenEditField_thenReturnBadRequest() {
        // Arrange
        List<FieldEditDataRequest<String>> fields = List.of(
            new FieldEditDataRequest<>("FIRST_NAME", "Jane")
        );
        UpdateCustomerRequest request = new UpdateCustomerRequest(fields);

        when(updateCustomerFieldUseCase.update(fields)).thenReturn(null);

        // Act
        ResponseEntity<CustomerResponse> response = controller.editField(request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(updateCustomerFieldUseCase).update(fields);
        verify(registerCustomerTimelineUseCase, never()).registerUpdateProfile();
    }
} 