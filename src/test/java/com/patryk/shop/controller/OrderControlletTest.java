package com.patryk.shop.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.patryk.shop.domain.dao.*;
import com.patryk.shop.repository.OrderRepository;
import com.patryk.shop.repository.ProductRepository;
import com.patryk.shop.repository.RoleRepository;
import com.patryk.shop.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.yml")
@ActiveProfiles("test")
public class OrderControlletTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @WithMockUser(username = "email@gmail.com")
    void shouldGetOrderByOrderNumber() throws Exception {

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

        var product = productRepository.save(Product.builder()
                .name("productName")
                .price(10.0)
                .quantity(5)
                .build());

        var order = orderRepository.save(Order.builder()
                .orderNumber("orderNumber123")
                .numberOfItems(5)
                .totalPrice(10.0)
                .status(OrderStatus.CREATED)
                .product(product)
                .user(user)
                .build());

        mockMvc.perform(get("/api/orders/" + order.getOrderNumber()))
                .andExpect(status().isOk());

    }

}
