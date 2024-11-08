package cleverton.heusner.adapter.input.validation.address.zipcode;

import cleverton.heusner.adapter.input.validation.ValidatorWithCustomTemplate;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

import static cleverton.heusner.adapter.input.constant.validation.AddressValidationErrorMessage.INVALID_ADDRESS_ZIPCODE;
import static cleverton.heusner.adapter.input.constant.validation.AddressValidationErrorMessage.NOT_BLANK_ADDRESS_ZIPCODE;

@Component
public class ZipCodeValidator extends ValidatorWithCustomTemplate implements ConstraintValidator<ZipCode, String> {

    private static final Pattern ZIPCODE_PATTERN = Pattern.compile("^\\d{8}$");

    @Override
    public boolean isValid(final String zipCode, final ConstraintValidatorContext context) {
        this.context = context;

        if (StringUtils.isBlank(zipCode)) {
            return createContext(NOT_BLANK_ADDRESS_ZIPCODE);
        }

        if (!ZIPCODE_PATTERN.matcher(zipCode).matches()) {
            return createContext(INVALID_ADDRESS_ZIPCODE, zipCode);
        }

        return true;
    }
}