package com.patryk.shop.controller;

import com.patryk.shop.domain.dto.OrderDto;
import com.patryk.shop.mapper.OrderMapper;
import com.patryk.shop.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @GetMapping("/{orderNumber}")
    @Operation(security = {
            @SecurityRequirement(name = "BasicAuth"),
            @SecurityRequirement(name = "bearer-key")
    })
    public OrderDto getOrderByOrderNumber(@PathVariable String orderNumber) {
        return orderMapper.orderToOrderDtos(orderService.getByOrderNumber(orderNumber));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(security = {
            @SecurityRequirement(name = "BasicAuth"),
            @SecurityRequirement(name = "bearer-key")
    })
    public OrderDto saveOrder() {
        return orderMapper.orderToOrderDtos(orderService.save());
    }

    @DeleteMapping("/{orderNumber}")
    @Operation(security = {
            @SecurityRequirement(name = "BasicAuth"),
            @SecurityRequirement(name = "bearer-key")
    })

    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrderById(@PathVariable String orderNumber) {
        orderService.deleteByOrderNumber(orderNumber);
    }
}
