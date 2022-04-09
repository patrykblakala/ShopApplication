package com.patryk.shop.controller;

import com.patryk.shop.domain.dto.OrderDto;
import com.patryk.shop.domain.dto.ProductDto;
import com.patryk.shop.domain.dto.UserDto;
import com.patryk.shop.mapper.HistoryMapper;
import com.patryk.shop.mapper.UserMapper;
import com.patryk.shop.repository.OrderRepository;
import com.patryk.shop.repository.ProductRepository;
import com.patryk.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/history")
@RequiredArgsConstructor
public class HistoryController {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final HistoryMapper historyMapper;
    private final UserMapper userMapper;


    @GetMapping("/users/{userId}")
    public Page<UserDto> getUsersHistory(@PathVariable Long userId, @RequestParam int page, @RequestParam int size) {
        return userRepository.findRevisions(userId, PageRequest.of(page, size)).map(historyMapper::toUserDto);
    }

    @GetMapping("/products/{userId}")
    public Page<ProductDto> getProductsHistory(@PathVariable Long productId, @RequestParam int page, @RequestParam int size) {
        return productRepository.findRevisions(productId, PageRequest.of(page, size)).map(historyMapper::toProductDto);
    }

    @GetMapping("/orders/{orderId}")
    public Page<OrderDto> getOrdersHistory(@PathVariable Long orderId, @RequestParam int page, @RequestParam int size) {
        return orderRepository.findRevisions(orderId, PageRequest.of(page, size)).map(historyMapper::toOrderDto);
    }

}
