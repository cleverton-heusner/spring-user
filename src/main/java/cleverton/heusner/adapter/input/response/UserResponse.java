package cleverton.heusner.adapter.input.response;

import cleverton.heusner.shared.model.UserAuditingData;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.context.annotation.PropertySource;

import static cleverton.heusner.adapter.input.configuration.message.MessageConfiguration.ENCODING;
import static cleverton.heusner.adapter.input.configuration.message.MessageConfiguration.FILE_FORMAT;
import static cleverton.heusner.adapter.input.constant.MessageClasspath.SCHEMA_MESSAGES;
import static cleverton.heusner.adapter.input.constant.doc.schema.UserSchemaDoc.USER;

@Schema(description = USER)
@JsonInclude(JsonInclude.Include.NON_NULL)
@PropertySource(value = SCHEMA_MESSAGES + FILE_FORMAT, encoding = ENCODING)
public record UserResponse(Long id,
                           String name,
                           String cpf,
                           String birthDate,
                           boolean active,
                           UserAuditingData userAuditingData,
                           AddressResponse addressResponse) {}