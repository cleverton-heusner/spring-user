package cleverton.heusner.adapter.output.mapper;

import cleverton.heusner.adapter.output.entity.UserEntity;
import cleverton.heusner.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface AddressEntityMapper {

    @Mapping(target = "addressEntity", source = "address")
    UserEntity toEntity(final User user);

    @Mapping(target = "address", source = "addressEntity")
    User toModel(final UserEntity userEntity);
}