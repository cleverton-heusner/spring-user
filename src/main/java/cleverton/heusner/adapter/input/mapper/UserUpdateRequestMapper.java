package cleverton.heusner.adapter.input.mapper;

import cleverton.heusner.adapter.input.request.user.UserUpdateRequest;
import cleverton.heusner.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserUpdateRequestMapper {

    @Mapping(source = "addressRequest", target = "address")

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)

    @Mapping(target = "userAuditingData.registerDateTime", ignore = true)
    @Mapping(target = "userAuditingData.lastUpdateDateTime", ignore = true)
    @Mapping(target = "userAuditingData.activationDateTime", ignore = true)
    @Mapping(target = "userAuditingData.deactivationDateTime", ignore = true)
    @Mapping(target = "userAuditingData.registerUser", ignore = true)
    @Mapping(target = "userAuditingData.lastUpdateUser", ignore = true)
    @Mapping(target = "userAuditingData.activationUser", ignore = true)
    @Mapping(target = "userAuditingData.deactivationUser", ignore = true)

    @Mapping(target = "address.state", ignore = true)
    @Mapping(target = "address.city", ignore = true)
    @Mapping(target = "address.neighborhood", ignore = true)
    @Mapping(target = "address.street", ignore = true)
    User toModel(final UserUpdateRequest userUpdateRequest);
}