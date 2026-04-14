package ru.test_app.user_service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.test_app.user_service.domain.Address;
import ru.test_app.user_service.domain.User;
import ru.test_app.user_service.model.AddressDto;
import ru.test_app.user_service.model.UserDto;
import ru.test_app.user_service.service.mapper.AddressMapper;
import ru.test_app.user_service.service.mapper.UserMapper;

import static org.junit.jupiter.api.Assertions.*;


public class UserMapperTest extends BaseIntegrationTest{

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AddressMapper addressMapper;

    @Test
    void toDto_mapsAddress() {
        Address address = new Address();
        address.setId(1L);
        address.setCity("Moscow");

        User user = new User();
        user.setId(1L);
        user.setAddress(address);

        UserDto dto = userMapper.toDto(user);

        assertNotNull(dto.getAddress());
        assertEquals(1L, dto.getAddress().getId());
    }

    @Test
    void toEntity_setsAddressId() {
        AddressDto addressDto = new AddressDto();
        addressDto.setId(5L);

        UserDto dto = new UserDto();
        dto.setAddress(addressDto);

        Address address = addressMapper.toEntity(addressDto);

        User user = userMapper.toEntity(dto);
        user.setAddress(address);

        assertNotNull(user.getAddress());
        assertEquals(5L, user.getAddress().getId());
    }
}
