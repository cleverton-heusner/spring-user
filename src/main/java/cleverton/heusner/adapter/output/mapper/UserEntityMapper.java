package cleverton.heusner.adapter.output.mapper;

import cleverton.heusner.adapter.output.entity.user.UserEntity;
import cleverton.heusner.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserEntityMapper {

    @Mappings({
        @Mapping(source = "address", target = "addressEntity"),
        @Mapping(source = "userAuditingData", target = "userAuditingDataEntity")
    })
    UserEntity toEntity(final User user);

    @Mappings({
            @Mapping(source = "addressEntity", target = "address"),
            @Mapping(source = "userAuditingDataEntity", target = "userAuditingData")
    })
    User toModel(final UserEntity userEntity);
}