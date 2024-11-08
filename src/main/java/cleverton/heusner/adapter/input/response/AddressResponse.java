package cleverton.heusner.adapter.input.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AddressResponse (
        String zipCode,
        String state,
        String city,
        String neighborhood,
        String street,
        String complement,
        String number) {
}