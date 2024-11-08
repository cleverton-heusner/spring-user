package cleverton.heusner.adapter.input.mapper;

import cleverton.heusner.adapter.input.response.UserResponse;
import cleverton.heusner.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserResponseMapper {

    @Mapping(source = "address", target = "addressResponse")
    UserResponse toResponse(final User user);
}