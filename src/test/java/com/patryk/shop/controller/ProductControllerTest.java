package com.patryk.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patryk.shop.domain.dao.Product;
import com.patryk.shop.domain.dto.ProductDto;
import com.patryk.shop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.yml")
@ActiveProfiles("test")
class ProductControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldGetProductById() throws Exception {

        var product = productRepository.save(Product.builder()
                .name("productName")
                .price(10.0)
                .quantity(5)
                .build());

        mockMvc.perform(get("/api/products/" + product.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.quantity").value(product.getQuantity()))
                .andExpect(jsonPath("$.price").value(product.getPrice()))
                .andExpect(jsonPath("$.revisionNumber").doesNotExist())
                .andExpect(jsonPath("$.operationType").doesNotExist());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldSaveProduct() throws Exception {

        var productDto = ProductDto.builder()
                .name("productDto")
                .quantity(5)
                .price(10.0)
                .build();

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "originalFile.jpg",
                MediaType.IMAGE_JPEG_VALUE, new byte[0]);

        MockMultipartFile jsonMockMultipartFile = new MockMultipartFile("productDto", "originalFile.jpg",
                MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsBytes(productDto));

        mockMvc.perform(multipart("/api/products")
                .file(mockMultipartFile)
                .file(jsonMockMultipartFile)
                .with(processor -> {
                    processor.setMethod(HttpMethod.POST.toString());
                    return processor;
                })
        ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(productDto.getName()))
                .andExpect(jsonPath("$.quantity").value(productDto.getQuantity()))
                .andExpect(jsonPath("$.price").value(productDto.getPrice()));

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldNotSaveProductWhenWrongExtensionFile() throws Exception {

        var productDto = ProductDto.builder()
                .name("productDto")
                .quantity(5)
                .price(10.0)
                .build();

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "originalFile.txt",
                MediaType.IMAGE_JPEG_VALUE, new byte[0]);

        MockMultipartFile jsonMockMultipartFile = new MockMultipartFile("productDto", "originalFile.jpg",
                MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsBytes(productDto));

        mockMvc.perform(multipart("/api/products")
                .file(mockMultipartFile)
                .file(jsonMockMultipartFile)
                .with(processor -> {
                    processor.setMethod(HttpMethod.POST.toString());
                    return processor;
                })
        ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist());

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldUpdateProduct() throws Exception {

        var product = productRepository.save(Product.builder()
                .name("product")
                .quantity(15)
                .price(7.0)
                .filePath("oldFilePath")
                .build());

        var productDto = ProductDto.builder()
                .name("productDto")
                .quantity(5)
                .price(10.0)
                .filePath("newFilePath")
                .build();

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "originalFile.jpg",
                MediaType.IMAGE_JPEG_VALUE, new byte[0]);

        MockMultipartFile productMultiPart = new MockMultipartFile("productDto", "",
                MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsBytes(productDto));

        mockMvc.perform(multipart("/api/products/" + product.getId())
                .file(mockMultipartFile)
                .file(productMultiPart)
                .with(processor -> {
                    processor.setMethod(HttpMethod.PUT.toString());
                    return processor;
                }))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(productDto.getName()))
                .andExpect(jsonPath("$.quantity").value(productDto.getQuantity()))
                .andExpect(jsonPath("$.price").value(productDto.getPrice()))
                .andExpect(jsonPath("$.filePath").value("target\\" + product.getId() + ".jpg"))
                .andExpect(jsonPath("$.revisionNumber").doesNotExist())
                .andExpect(jsonPath("$.operationType").doesNotExist());

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldNotUpdateAnUnExistingProduct() throws Exception {

        var productDto = ProductDto.builder()
                .name("productDto")
                .quantity(5)
                .price(10.0)
                .build();

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "originalFile.jpg",
                MediaType.IMAGE_JPEG_VALUE, new byte[0]);

        MockMultipartFile productMultiPart = new MockMultipartFile("productDto", "",
                MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsBytes(productDto));


        mockMvc.perform(multipart("/api/products/5")
                .file(mockMultipartFile)
                .file(productMultiPart)
                .with(processor -> {
                    processor.setMethod(HttpMethod.PUT.toString());
                    return processor;
                }))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist());

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldDeleteProductById() throws Exception {

        var product = productRepository.save(Product.builder()
                .name("productDto")
                .quantity(5)
                .price(10.0)
                .build());

        mockMvc.perform(delete("/api/products/" + product.getId()))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldNotDeleteProductByIdIfProductDoesNotExist() throws Exception {

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist());
    }
}
