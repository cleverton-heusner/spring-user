package cleverton.heusner.adapter.input.constant.validation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserValidationErrorMessage {

    public static final byte USER_NAME_MIN_SIZE = 1;
    public static final byte USER_NAME_MAX_SIZE = 30;
    public static final byte USER_CPF_SIZE = 11;

    public static final String SIZE_USER_NAME = "{Size.user.name}";
    public static final String NOT_BLANK_USER_BIRTHDATE = "{NotBlank.user.birthdate}";
    public static final String INVALID_USER_CPF = "{Invalid.user.cpf}";
}