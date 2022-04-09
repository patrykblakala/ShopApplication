package com.patryk.shop.controller;

import com.patryk.shop.domain.dto.BasketDto;
import com.patryk.shop.domain.dto.ProductDto;
import com.patryk.shop.mapper.ProductMapper;
import com.patryk.shop.service.BasketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/baskets")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class BasketController {

    private final BasketService basketService;
    private final ProductMapper productMapper;

    @GetMapping
    @Operation(security = {
            @SecurityRequirement(name = "BasicAuth"),
            @SecurityRequirement(name = "bearer-key")
    })
    public List<ProductDto> getBasket() {
        return productMapper.productsListDto((basketService.getProductsInBasket()));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(security = {
            @SecurityRequirement(name = "BasicAuth"),
            @SecurityRequirement(name = "bearer-key")
    })
    public void saveProductsToBasket(@RequestBody @Valid BasketDto basketDto) {
        basketService.addToBasket(basketDto.getProductId(), basketDto.getQuantity());
    }

    @DeleteMapping("/{id}/product")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(security = {
            @SecurityRequirement(name = "BasicAuth"),
            @SecurityRequirement(name = "bearer-key")
    })
    public void deleteProductFromBasket(@PathVariable Long id) {
        basketService.deleteProduct(id);
    }
}
