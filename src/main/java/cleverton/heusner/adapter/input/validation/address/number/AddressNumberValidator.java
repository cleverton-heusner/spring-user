package cleverton.heusner.adapter.input.validation.address.number;

import cleverton.heusner.adapter.input.validation.ValidatorWithCustomTemplate;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

import static cleverton.heusner.adapter.input.constant.validation.AddressValidationErrorMessage.*;

@Component
public class AddressNumberValidator extends ValidatorWithCustomTemplate implements ConstraintValidator<AddressNumber, String> {

    private static final Pattern ADDRESS_NUMBER_PATTERN = Pattern.compile("^\\d{1,5}$");

    @Override
    public boolean isValid(final String addressNumber, final ConstraintValidatorContext context) {
        this.context = context;

        if (StringUtils.isBlank(addressNumber)) {
            return createContext(NOT_BLANK_ADDRESS_NUMBER);
        }

        if (!ADDRESS_NUMBER_PATTERN.matcher(addressNumber).matches()) {
            return createContext(INVALID_ADDRESS_NUMBER, addressNumber);
        }

        return true;
    }
}