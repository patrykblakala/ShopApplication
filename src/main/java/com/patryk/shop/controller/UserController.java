package com.patryk.shop.controller;

import com.patryk.shop.domain.dto.UserDto;
import com.patryk.shop.mapper.UserMapper;
import com.patryk.shop.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{id}")
    @Operation(security = {
            @SecurityRequirement(name = "BasicAuth"),
            @SecurityRequirement(name = "bearer-key")
    })

    @PreAuthorize("isAuthenticated()")
    public UserDto getUserById(@PathVariable Long id) {
        return userMapper.userDaoToUserDto(userService.getById(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = {
            @SecurityRequirement(name = "BasicAuth"),
            @SecurityRequirement(name = "bearer-key")
    })
    public Page<UserDto> pageUser(@RequestParam int page, @RequestParam int size) {
        return userService.page(PageRequest.of(page, size))
                .map(userMapper::userDaoToUserDto);
    }


    @PutMapping("/{id}")
    @Operation(security = {
            @SecurityRequirement(name = "BasicAuth"),
            @SecurityRequirement(name = "bearer-key")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto updateUser(@RequestBody UserDto userDto, @PathVariable Long id) {
        return userMapper.userDaoToUserDto(userService.update(userMapper.userDtoToUser(userDto), id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addUser(@RequestBody @Valid UserDto userDto) {
        return userMapper.userDaoToUserDto(userService.save(userMapper.userDtoToUser(userDto)));
    }

    @DeleteMapping("/{id}")
    @Operation(security = {
            @SecurityRequirement(name = "BasicAuth"),
            @SecurityRequirement(name = "bearer-key")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
