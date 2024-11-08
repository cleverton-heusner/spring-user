package cleverton.heusner.adapter.input.constant.validation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressValidationErrorMessage {

    public static final String NOT_BLANK_ADDRESS_NUMBER = "NotBlank.address.number";
    public static final String NOT_BLANK_ADDRESS_ZIPCODE = "NotBlank.address.zipcode";
    public static final String INVALID_ADDRESS_NUMBER =  "Invalid.address.number";
    public static final String INVALID_ADDRESS_ZIPCODE =  "Invalid.address.zipcode";
    public static final String SIZE_ADDRESS_COMPLEMENT = "{Size.address.complement}";
}