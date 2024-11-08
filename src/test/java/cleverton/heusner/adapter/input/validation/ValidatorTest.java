package cleverton.heusner.adapter.input.validation;

import cleverton.heusner.port.shared.MessageComponent;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ValidatorTest {

    @Mock
    protected ConstraintValidatorContext context;

    @Mock
    protected ConstraintValidatorContext.ConstraintViolationBuilder constraintViolationBuilder;

    @Mock
    protected MessageComponent messageComponent;

    protected void mockConstraintValidatorContext() {
        doNothing().when(context).disableDefaultConstraintViolation();
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(constraintViolationBuilder);
        when(constraintViolationBuilder.addConstraintViolation()).thenReturn(context);
    }

    protected void verifyConstraintValidatorContext() {
        verify(context).disableDefaultConstraintViolation();
        verify(context).buildConstraintViolationWithTemplate(anyString());
        verify(constraintViolationBuilder).addConstraintViolation();
    }
}
