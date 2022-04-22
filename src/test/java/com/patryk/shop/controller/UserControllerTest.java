package com.patryk.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patryk.shop.domain.dao.Role;
import com.patryk.shop.domain.dao.User;
import com.patryk.shop.domain.dto.UserDto;
import com.patryk.shop.repository.RoleRepository;
import com.patryk.shop.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.history.RevisionMetadata;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.yml")
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Test
    @WithMockUser
    void shouldGetUserById() throws Exception {

        Set<Role> roles = new HashSet<>();
        Role role = new Role();
        role.setName("ADMIN");
        roleRepository.save(role);
        roles.add(role);

        var user = userRepository.save(User.builder()
                .password("123")
                .email("email@gmail.com")
                .roles(roles)
                .build());


        mockMvc.perform(get("/api/users/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.roles", containsInAnyOrder("ADMIN")))
                .andExpect(jsonPath("$.confirmPassword").doesNotExist())
                .andExpect(jsonPath("$.revisionNumber").doesNotExist())
                .andExpect(jsonPath("$.operationType").doesNotExist());
    }

    @Test
    @WithMockUser(username = "password")
    void shouldSaveUser() throws Exception {

        Set<Role> roles = new HashSet<>();
        Role role = new Role();
        role.setName("ADMIN");
        roleRepository.save(role);
        roles.add(role);

        var userDto = UserDto.builder()
                .password("password")
                .email("email@gmail.com")
                .confirmPassword("password")
                .build();


        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.email").value(userDto.getEmail()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldUpdateUser() throws Exception {

        Set<Role> roles = new HashSet<>();
        Role role = new Role();
        role.setName("ADMIN");
        roleRepository.save(role);
        roles.add(role);

        var user = userRepository.save(User.builder()
                .password("password")
                .email("email@gmail.com")
                .roles(roles)
                .build());

        var userDto = UserDto.builder()
                .password("password1")
                .email("email1@gmail.com")
                .confirmPassword("password1")
                .build();


        mockMvc.perform(put("/api/users/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.email").value(userDto.getEmail()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldPageUser() throws Exception {

        Set<Role> roles = new HashSet<>();
        Role role = new Role();
        role.setName("ADMIN");
        roleRepository.save(role);
        roles.add(role);

        var user1 = userRepository.save(User.builder()
                .password("password")
                .email("email@gmail.com")
                .roles(roles)
                .build());

        var user2 = userRepository.save(User.builder()
                .password("password1")
                .email("email1@gmail.com")
                .roles(roles)
                .build());


        mockMvc.perform(get("/api/users")
                .queryParam("page", "0")
                .queryParam("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldDeleteUser() throws Exception {

        Set<Role> roles = new HashSet<>();
        Role role = new Role();
        role.setName("ADMIN");
        roleRepository.save(role);
        roles.add(role);

        var user = userRepository.save(User.builder()
                .password("password")
                .email("email@gmail.com")
                .roles(roles)
                .build());

        mockMvc.perform(delete("/api/users/" + user.getId()))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());

    }
}
