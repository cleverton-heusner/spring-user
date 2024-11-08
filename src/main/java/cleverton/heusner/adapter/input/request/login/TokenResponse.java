package cleverton.heusner.adapter.input.request.login;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.context.annotation.PropertySource;

import static cleverton.heusner.adapter.input.configuration.message.MessageConfiguration.ENCODING;
import static cleverton.heusner.adapter.input.configuration.message.MessageConfiguration.FILE_FORMAT;
import static cleverton.heusner.adapter.input.constant.MessageClasspath.SCHEMA_MESSAGES;
import static cleverton.heusner.adapter.input.constant.doc.schema.LoginSchemaDoc.LOGIN;

@Schema(description = LOGIN)
@PropertySource(value = SCHEMA_MESSAGES + FILE_FORMAT, encoding = ENCODING)
public record TokenResponse(String token) {}
