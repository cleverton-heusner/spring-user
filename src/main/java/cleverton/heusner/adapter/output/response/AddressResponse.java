package cleverton.heusner.adapter.output.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import static cleverton.heusner.adapter.input.configuration.message.MessageConfiguration.ENCODING;
import static cleverton.heusner.adapter.input.configuration.message.MessageConfiguration.FILE_FORMAT;
import static cleverton.heusner.adapter.input.constant.MessageClasspath.SCHEMA_MESSAGES;
import static cleverton.heusner.adapter.input.constant.doc.schema.AddressSchemaDoc.ADDRESS_COMPLETE;


@Schema(description = ADDRESS_COMPLETE)
@PropertySource(value = SCHEMA_MESSAGES + FILE_FORMAT, encoding = ENCODING)
@Validated
public record AddressResponse(
        @JsonProperty("cep")
        String zipCode,

        @JsonProperty("uf")
        String state,

        @JsonProperty("localidade")
        String city,

        @JsonProperty("bairro")
        String neighborhood,

        @JsonProperty("logradouro")
        String street,

        @JsonProperty("complemento")
        String complement) {}