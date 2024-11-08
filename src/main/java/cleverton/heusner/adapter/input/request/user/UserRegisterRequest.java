package cleverton.heusner.adapter.input.request.user;

import cleverton.heusner.adapter.input.request.address.AddressRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

import static cleverton.heusner.adapter.input.configuration.message.MessageConfiguration.ENCODING;
import static cleverton.heusner.adapter.input.configuration.message.MessageConfiguration.FILE_FORMAT;
import static cleverton.heusner.adapter.input.constant.MessageClasspath.SCHEMA_MESSAGES;
import static cleverton.heusner.adapter.input.constant.doc.schema.UserSchemaDoc.*;
import static cleverton.heusner.adapter.input.constant.validation.UserValidationErrorMessage.*;

@Schema(description = USER_REGISTER)
@PropertySource(value = SCHEMA_MESSAGES + FILE_FORMAT, encoding = ENCODING)
@Data
@Validated
public class UserRegisterRequest {

    @Schema(
            description = USER_NAME,
            minLength = USER_NAME_MIN_SIZE,
            maxLength = USER_NAME_MAX_SIZE,
            example = "Cleverton Heusner"
    )
    @Size(
            min = USER_NAME_MIN_SIZE,
            max = USER_NAME_MAX_SIZE,
            message = SIZE_USER_NAME
    )
    private String name;

    @Schema(
            description = USER_CPF,
            minLength = USER_CPF_SIZE,
            maxLength = USER_CPF_SIZE,
            example = "35811365039"
    )
    @CPF(message = INVALID_USER_CPF)
    private String cpf;

    @Schema(description = USER_BIRTH_DATE, example = "2000-01-01")
    @NotNull(message = NOT_BLANK_USER_BIRTHDATE)
    private LocalDate birthDate;

    @Valid
    private AddressRequest addressRequest;
}