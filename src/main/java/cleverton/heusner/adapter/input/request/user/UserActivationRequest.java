package cleverton.heusner.adapter.input.request.user;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import static cleverton.heusner.adapter.input.configuration.message.MessageConfiguration.ENCODING;
import static cleverton.heusner.adapter.input.configuration.message.MessageConfiguration.FILE_FORMAT;
import static cleverton.heusner.adapter.input.constant.MessageClasspath.SCHEMA_MESSAGES;
import static cleverton.heusner.adapter.input.constant.doc.schema.UserSchemaDoc.USER_ACTIVATION;

@Schema(description = USER_ACTIVATION)
@PropertySource(value = SCHEMA_MESSAGES + FILE_FORMAT, encoding = ENCODING)
@Validated
public record UserActivationRequest(
        @Schema(description = USER_ACTIVATION, example = "false")
        boolean active
) {}