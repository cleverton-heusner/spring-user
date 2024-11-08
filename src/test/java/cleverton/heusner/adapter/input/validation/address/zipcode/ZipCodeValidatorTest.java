package cleverton.heusner.adapter.input.validation.address.zipcode;

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

public class ZipCodeValidatorTest extends ValidatorTest {

    @InjectMocks
    private ZipCodeValidator zipCodeValidator;

    @Test
    void when_AddressZipCodeIsValid_then_AddressZipCodeValidationReturnTrue() {

        // Arrange
        final String addressZipCode = "12345678";

        // Act
        final boolean isAddressZipCodeValid = zipCodeValidator.isValid(addressZipCode, context);

        // Assert
        assertThat(isAddressZipCodeValid).isTrue();
    }

    @Test
    void when_AddressZipCodeIsBlank_then_AddressZipCodeValidationReturnFalse() {

        // Arrange
        when(messageComponent.getMessage(anyString(), any(Object[].class))).thenReturn("Address ZIP code is blank.");
        mockConstraintValidatorContext();

        // Act
        final boolean isAddressZipCodeValid = zipCodeValidator.isValid("", context);

        // Assert
        assertThat(isAddressZipCodeValid).isFalse();

        verify(messageComponent).getMessage(anyString(), any(Object[].class));
        verifyConstraintValidatorContext();
    }
    @Test
    void when_AddressZipCodeIsNull_then_AddressZipCodeValidationReturnFalse() {

        // Arrange
        when(messageComponent.getMessage(anyString(), any(Object[].class))).thenReturn("Address ZIP code is null.");
        mockConstraintValidatorContext();

        // Act
        final boolean isAddressZipCodeValid = zipCodeValidator.isValid(null, context);

        // Assert
        assertThat(isAddressZipCodeValid).isFalse();

        verify(messageComponent).getMessage(anyString(), any(Object[].class));
        verifyConstraintValidatorContext();
    }

    @ParameterizedTest
    @MethodSource("getInvalidAddressZipCodes")
    void when_AddressZipCodeIsInvalid_then_AddressZipCodeValidationReturnFalse(final String invalidAddressZipCode) {

        // Arrange
        when(messageComponent.getMessage(anyString(), any(Object[].class))).thenReturn("Address ZIP code is invalid.");
        mockConstraintValidatorContext();

        // Act
        final boolean isAddressZipCodeValid = zipCodeValidator.isValid(invalidAddressZipCode, context);

        // Assert
        assertThat(isAddressZipCodeValid).isFalse();

        verify(messageComponent).getMessage(anyString(), any(Object[].class));
        verifyConstraintValidatorContext();
    }

    private static Stream<Arguments> getInvalidAddressZipCodes() {
        return Stream.of(
                Arguments.of("1"),
                Arguments.of("1234567"),
                Arguments.of("123456789"),
                Arguments.of(" 12345678 "),
                Arguments.of("a"),
                Arguments.of("abcdefgh"),
                Arguments.of("abcdefghi")
        );
    }
}