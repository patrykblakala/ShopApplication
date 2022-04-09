package com.patryk.shop.controller;


import com.patryk.shop.domain.dto.ProductDto;
import com.patryk.shop.mapper.ProductMapper;
import com.patryk.shop.service.ProductService;
import com.patryk.shop.validator.ExtensionValid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Validated
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        return productMapper.productDaoToProductDto(productService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(security = {
            @SecurityRequirement(name = "BasicAuth"),
            @SecurityRequirement(name = "bearer-key")
    })
    public ProductDto saveProduct(@RequestPart ProductDto productDto, @RequestPart @Valid @ExtensionValid MultipartFile file) {
        return productMapper.productDaoToProductDto(productService.save(productMapper.productDtoToProduct(productDto)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = {
            @SecurityRequirement(name = "BasicAuth"),
            @SecurityRequirement(name = "bearer-key")
    })
    public ProductDto updateProduct(@RequestBody ProductDto productDto, @PathVariable Long id) {
        return productMapper.productDaoToProductDto(productService.update(productMapper.productDtoToProduct(productDto), id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = {
            @SecurityRequirement(name = "BasicAuth"),
            @SecurityRequirement(name = "bearer-key")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteById(id);
    }

}
