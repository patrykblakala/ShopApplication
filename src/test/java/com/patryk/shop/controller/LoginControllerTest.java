package com.patryk.shop.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.patryk.shop.domain.dao.Role;
import com.patryk.shop.domain.dao.User;
import com.patryk.shop.domain.dto.LoginDto;
import com.patryk.shop.repository.RoleRepository;
import com.patryk.shop.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.yml")
@ActiveProfiles("test")
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void shouldLogIn() throws Exception {

        var role = new Role();
        role.setName("USER_ROLE");
        roleRepository.save(role);
        var roles = new HashSet<Role>();
        roles.add(role);
        User user = userRepository.save(User.builder()
                .email("email@gmail.com")
                .password(passwordEncoder.encode("myPassword"))
                .roles(roles)
                .build());
        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(LoginDto.builder()
                                .email(user.getEmail())
                                .password("myPassword")
                                .build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void shouldNotLogInWIthIncorrectPassword() throws Exception {
        var role = new Role();
        role.setName("USER_ROLE");
        roleRepository.save(role);
        var roles = new HashSet<Role>();
        roles.add(role);
        User user = userRepository.save(User.builder()
                .email("email@gmail.com")
                .password(passwordEncoder.encode("myPassword"))
                .roles(roles)
                .build());
        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(LoginDto.builder()
                                .email(user.getEmail())
                                .password("differentPassword")
                                .build())))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$").doesNotExist());
    }
}
