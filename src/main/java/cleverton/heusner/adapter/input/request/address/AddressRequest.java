package cleverton.heusner.adapter.input.request.address;

import cleverton.heusner.adapter.input.validation.address.number.AddressNumber;
import cleverton.heusner.adapter.input.validation.address.zipcode.ZipCode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import static cleverton.heusner.adapter.input.configuration.message.MessageConfiguration.ENCODING;
import static cleverton.heusner.adapter.input.configuration.message.MessageConfiguration.FILE_FORMAT;
import static cleverton.heusner.adapter.input.constant.MessageClasspath.SCHEMA_MESSAGES;
import static cleverton.heusner.adapter.input.constant.doc.schema.AddressSchemaDoc.*;
import static cleverton.heusner.adapter.input.constant.validation.AddressValidationErrorMessage.SIZE_ADDRESS_COMPLEMENT;

@Schema(description = ADDRESS_INCOMPLETE)
@PropertySource(value = SCHEMA_MESSAGES + FILE_FORMAT, encoding = ENCODING)
@Validated
public record AddressRequest(
        @Schema(
                description = ADDRESS_ZIPCODE,
                minLength = 8,
                maxLength = 8,
                example = "04563050"
        )
        @ZipCode
        String zipCode,


        @Schema(
                description = ADDRESS_COMPLEMENT,
                minLength = 1,
                maxLength = 30,
                example = "Próximo à biblioteca"
        )
        @Size(
                min = 1,
                max = 30,
                message = SIZE_ADDRESS_COMPLEMENT
        )
        String complement,

        @Schema(
                description = ADDRESS_NUMBER,
                minLength = 1,
                maxLength = 5,
                example = "35"
        )
        @AddressNumber
        String number) {}