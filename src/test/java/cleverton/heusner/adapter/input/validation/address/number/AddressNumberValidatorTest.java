package cleverton.heusner.adapter.input.validation.address.number;

import cleverton.heusner.adapter.input.validation.ValidatorTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class AddressNumberValidatorTest extends ValidatorTest {

    @InjectMocks
    private AddressNumberValidator addressNumberValidator;

    @Test
    void when_AddressNumberHasOneDigit_then_AddressNumberValidationReturnTrue() {

        // Arrange
        final String addressNumber = "1";

        // Act
        final boolean isAddressNumberValid = addressNumberValidator.isValid(addressNumber, context);

        // Assert
        assertThat(isAddressNumberValid).isTrue();
    }

    @Test
    void when_AddressNumberHasFiveDigits_then_AddressNumberValidationReturnTrue() {

        // Arrange
        final String addressNumber = "12345";

        // Act
        final boolean isAddressNumberValid = addressNumberValidator.isValid(addressNumber, context);

        // Assert
        assertThat(isAddressNumberValid).isTrue();
    }

    @Test
    void when_AddressNumberIsBlank_then_AddressNumberValidationReturnFalse() {

        // Arrange
        when(messageComponent.getMessage(anyString(), any(Object[].class))).thenReturn("Address number is blank.");
        mockConstraintValidatorContext();

        // Act
        final boolean isAddressNumberValid = addressNumberValidator.isValid("", context);

        // Assert
        assertThat(isAddressNumberValid).isFalse();

        verify(messageComponent).getMessage(anyString(), any(Object[].class));
        verifyConstraintValidatorContext();
    }
    @Test
    void when_AddressNumberIsNull_then_AddressNumberValidationReturnFalse() {

        // Arrange
        when(messageComponent.getMessage(anyString(), any(Object[].class))).thenReturn("Address number is null.");
        mockConstraintValidatorContext();

        // Act
        final boolean isAddressNumberValid = addressNumberValidator.isValid(null, context);

        // Assert
        assertThat(isAddressNumberValid).isFalse();

        verify(messageComponent).getMessage(anyString(), any(Object[].class));
        verifyConstraintValidatorContext();
    }

    @ParameterizedTest
    @MethodSource("getInvalidAddressNumbers")
    void when_AddressNumberIsInvalid_then_AddressNumberValidationReturnFalse(final String invalidAddressNumber) {

        // Arrange
        when(messageComponent.getMessage(anyString(), any(Object[].class))).thenReturn("Address number invalid.");
        mockConstraintValidatorContext();

        // Act
        final boolean isAddressNumberValid = addressNumberValidator.isValid(invalidAddressNumber, context);

        // Assert
        assertThat(isAddressNumberValid).isFalse();

        verify(messageComponent).getMessage(anyString(), any(Object[].class));
        verifyConstraintValidatorContext();
    }

    private static Stream<Arguments> getInvalidAddressNumbers() {
        return Stream.of(
                Arguments.of("123456"),
                Arguments.of(" 123456 "),
                Arguments.of("a"),
                Arguments.of("abcde"),
                Arguments.of("abcdef")
        );
    }
}
