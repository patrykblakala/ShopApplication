package com.patryk.shop.mapper;

import com.patryk.shop.domain.dao.Role;
import com.patryk.shop.domain.dao.User;
import com.patryk.shop.domain.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper extends AuditableMapper<User, UserDto>{

    @Mapping(source = "roles", target ="roles", qualifiedByName = "rolesMapper")
    @Mapping(target = "password", ignore = true)
    UserDto userDaoToUserDto(User user);

    @Mapping(target = "roles", ignore = true)
    User userDtoToUser(UserDto userDto);

    @Named("rolesMapper")
    default Set<String> rolesMapper(Set<Role> roles) {
        if(roles == null) return null;
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }
}
