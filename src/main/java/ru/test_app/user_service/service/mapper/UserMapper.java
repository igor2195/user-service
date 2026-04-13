package ru.test_app.user_service.service.mapper;

import org.mapstruct.*;
import ru.test_app.user_service.domain.User;
import ru.test_app.user_service.model.UserDto;

@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface UserMapper {
    @Mapping(source = "address", target = "address")
    UserDto toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "address", target = "address")
    User toEntity(UserDto userDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "address", target = "address")
    void update(@MappingTarget User entity, UserDto dto);
}
