package ru.test_app.user_service.service.mapper;

import org.mapstruct.*;
import ru.test_app.user_service.domain.Address;
import ru.test_app.user_service.domain.User;
import ru.test_app.user_service.model.UserDto;

/**
 * Маппер для {@link User}
 */
@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface UserMapper {

    UserDto toDto(User user);

    @Mapping(target = "address", ignore = true)
    User toEntity(UserDto userDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "address", ignore = true)
    void update(@MappingTarget User entity, UserDto dto);
}
