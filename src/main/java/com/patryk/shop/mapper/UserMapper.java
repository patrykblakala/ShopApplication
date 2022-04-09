package com.patryk.shop.mapper;

import com.patryk.shop.domain.dao.User;
import com.patryk.shop.domain.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends AuditableMapper<User, UserDto>{
    UserDto userDaoToUserDto(User user);

    User userDtoToUser(UserDto userDto);
}
