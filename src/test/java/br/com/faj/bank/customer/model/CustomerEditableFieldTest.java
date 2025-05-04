package br.com.faj.bank.customer.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CustomerEditableFieldTest {

    @ParameterizedTest
    @MethodSource("provideValidFieldTypes")
    void givenValidFieldType_whenFromString_thenReturnCorrectEnum(String input, CustomerEditableField expected) {
        // Act
        CustomerEditableField result = CustomerEditableField.fromString(input);

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void givenInvalidFieldType_whenFromString_thenThrowIllegalArgumentException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> CustomerEditableField.fromString("INVALID_FIELD"));
    }

    private static Stream<Arguments> provideValidFieldTypes() {
        return Stream.of(
            Arguments.of("LAST_NAME", CustomerEditableField.LAST_NAME),
            Arguments.of("FIRST_NAME", CustomerEditableField.FIRST_NAME),
            Arguments.of("EMAIL", CustomerEditableField.EMAIL)
        );
    }
} 