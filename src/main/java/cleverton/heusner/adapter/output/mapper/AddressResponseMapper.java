package cleverton.heusner.adapter.output.mapper;

import cleverton.heusner.adapter.output.response.AddressResponse;
import cleverton.heusner.domain.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface AddressResponseMapper {

    @Mapping(target = "complement", expression = "java(mapComplement(addressResponse, address))")
    @Mapping(target = "zipCode", expression = "java(mapZipCode(addressResponse))")
    @Mapping(target = "state", source = "addressResponse.state")
    @Mapping(target = "city", source = "addressResponse.city")
    @Mapping(target = "neighborhood", source = "addressResponse.neighborhood")
    @Mapping(target = "street", source = "addressResponse.street")
    Address toModel(final AddressResponse addressResponse, final Address address);

    default String mapComplement(final AddressResponse addressResponse, final Address address) {
        return address.isComplementBlank() ? addressResponse.complement() : address.getComplement();
    }

    default String mapZipCode(final AddressResponse addressResponse) {
        return formatZipCode(addressResponse.zipCode());
    }

    private String formatZipCode(final String zipCode) {
        return zipCode.replace("-", "");
    }
}