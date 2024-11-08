package cleverton.heusner.adapter.input.request.login;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import static cleverton.heusner.adapter.input.configuration.message.MessageConfiguration.ENCODING;
import static cleverton.heusner.adapter.input.configuration.message.MessageConfiguration.FILE_FORMAT;
import static cleverton.heusner.adapter.input.constant.MessageClasspath.SCHEMA_MESSAGES;
import static cleverton.heusner.adapter.input.constant.doc.schema.LoginSchemaDoc.*;

@Schema(description = LOGIN_AUTHENTICATION)
@PropertySource(value = SCHEMA_MESSAGES + FILE_FORMAT, encoding = ENCODING)
@Validated
public record LoginAuthenticationRequest(
        @Schema(
                description = LOGIN_USERNAME,
                example = "Fox"
        )
        String username,

        @Schema(
                description = LOGIN_PASSWORD,
                example = "123"
        )
        String password) {}