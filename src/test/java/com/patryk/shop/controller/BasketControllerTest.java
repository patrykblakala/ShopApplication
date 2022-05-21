package com.patryk.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patryk.shop.domain.dao.Basket;
import com.patryk.shop.domain.dao.Product;
import com.patryk.shop.domain.dao.Role;
import com.patryk.shop.domain.dao.User;
import com.patryk.shop.domain.dto.BasketDto;
import com.patryk.shop.repository.BasketRepository;
import com.patryk.shop.repository.ProductRepository;
import com.patryk.shop.repository.RoleRepository;
import com.patryk.shop.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.yml")
@ActiveProfiles("test")
public class BasketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    @WithMockUser(username = "email@gmail.com")
    void shouldGetBasket() throws Exception {

        Set<Role> roles = new HashSet<>();
        Role role = new Role();
        role.setName("ADMIN");
        roleRepository.save(role);
        roles.add(role);

        var user = userRepository.save(User.builder()
                .email("email@gmail.com")
                .password("password")
                .roles(roles)
                .build());

        var product1 = productRepository.save(Product.builder()
                .name("product1")
                .price(10.0)
                .quantity(5)
                .build());

        var product2 = productRepository.save(Product.builder()
                .name("product2")
                .price(8.0)
                .quantity(5)
                .build());
        var productList = new ArrayList<Product>();
        productList.add(product1);
        productList.add(product2);

        var basket = basketRepository.save(Basket.builder()
                .product(product1)
                .quantity(3)
                .user(user)
                .build());

        mockMvc.perform(get("/api/baskets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[*].id").value(product1.getId().intValue()))
                .andExpect(jsonPath("$[*].price").value(product1.getPrice()))
                .andExpect(jsonPath("$[*].name").value(product1.getName()))
                .andExpect(jsonPath("$[*].quantity").value(product1.getQuantity()));
    }

    @Test
    void shouldNotGetBasketUnAuthorized() throws Exception {

        mockMvc.perform(get("/api/baskets"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "email@gmail.com")
    void shouldSaveProductsToBasket() throws Exception {

        Set<Role> roles = new HashSet<>();
        Role role = new Role();
        role.setName("ADMIN");
        roleRepository.save(role);
        roles.add(role);

        var user = userRepository.save(User.builder()
                .email("email@gmail.com")
                .password("password")
                .roles(roles)
                .build());

        var product1 = productRepository.save(Product.builder()
                .name("product1")
                .price(10.0)
                .quantity(5)
                .build());

        var product2 = productRepository.save(Product.builder()
                .name("product2")
                .price(8.0)
                .quantity(5)
                .build());
        var productList = new ArrayList<Product>();
        productList.add(product1);
        productList.add(product2);

        var basketDto = BasketDto.builder()
                .productId(product1.getId())
                .quantity(3)
                .build();

        mockMvc.perform(post("/api/baskets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(basketDto)))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser(username = "email@gmail.com")
    void shouldDeleteProductFromBasket() throws Exception {

        Set<Role> roles = new HashSet<>();
        Role role = new Role();
        role.setName("ADMIN");
        roleRepository.save(role);
        roles.add(role);

        var user = userRepository.save(User.builder()
                .email("email@gmail.com")
                .password("password")
                .roles(roles)
                .build());

        var product1 = productRepository.save(Product.builder()
                .name("product1")
                .price(10.0)
                .quantity(5)
                .build());

        var productList = new ArrayList<Product>();
        productList.add(product1);

        var basketDto = BasketDto.builder()
                .productId(product1.getId())
                .quantity(3)
                .build();

        mockMvc.perform(delete("/api/baskets/" + product1.getId() + "/product"))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser(username = "email@gmail.com")
    void shouldNotDeleteProductIfDoesNotExists() throws Exception {

        Set<Role> roles = new HashSet<>();
        Role role = new Role();
        role.setName("ADMIN");
        roleRepository.save(role);
        roles.add(role);

        var user = userRepository.save(User.builder()
                .email("email@gmail.com")
                .password("password")
                .roles(roles)
                .build());

        var product1 = productRepository.save(Product.builder()
                .name("product1")
                .price(10.0)
                .quantity(5)
                .build());

        var productList = new ArrayList<Product>();
        productList.add(product1);

        var basketDto = BasketDto.builder()
                .productId(product1.getId())
                .quantity(3)
                .build();

        mockMvc.perform(delete("/api/baskets/100/product"))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());
    }

}
