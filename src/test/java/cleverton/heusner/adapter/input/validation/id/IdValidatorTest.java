package cleverton.heusner.adapter.input.validation.id;

import cleverton.heusner.adapter.input.validation.ValidatorTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class IdValidatorTest extends ValidatorTest {

    @InjectMocks
    private IdValidator idValidator;

    @Test
    void whenIdParameterIsPositiveNumber_thenIdParameterValidationReturnTrue() {

        // Arrange
        final String idAsPositiveNumber = "1";

        // Act
        boolean isAgeValid = idValidator.isValid(idAsPositiveNumber, context);

        // Assert
        assertThat(isAgeValid).isTrue();
    }

    @ParameterizedTest
    @MethodSource("getInvalidIds")
    void whenIdParameterIsInvalid_thenIdParameterValidationReturnFalse(final String invalidId) {

        // Arrange
        when(messageComponent.getMessage(anyString(), any(Object[].class))).thenReturn("Id inválido.");
        mockConstraintValidatorContext();

        // Act
        boolean isAgeValid = idValidator.isValid(invalidId, context);

        // Assert
        assertThat(isAgeValid).isFalse();

        verify(messageComponent).getMessage(anyString(), any(Object[].class));
        verifyConstraintValidatorContext();
    }

    private static Stream<Arguments> getInvalidIds() {
        return Stream.of(
                Arguments.of("0"),
                Arguments.of("-1"),
                Arguments.of("a"),
                Arguments.of("A"),
                Arguments.of(""),
                Arguments.of("  "),
                Arguments.of(" 1 "),
                Arguments.of((Object) null)
        );
    }
}