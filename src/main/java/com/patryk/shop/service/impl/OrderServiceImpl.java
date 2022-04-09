package com.patryk.shop.service.impl;

import com.patryk.shop.domain.dao.Order;
import com.patryk.shop.domain.dao.OrderStatus;
import com.patryk.shop.domain.dao.Product;
import com.patryk.shop.repository.OrderRepository;
import com.patryk.shop.service.BasketService;
import com.patryk.shop.service.OrderService;
import com.patryk.shop.service.ProductService;
import com.patryk.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final BasketService basketService;
    private final ProductService productService;

    @Override
    public List<Order> save() {
        var orderNumber = UUID.randomUUID().toString();
        var currentUser = userService.getCurrentUser();

        List<Product> productsInBasket = basketService.getProductsInBasket();

        var order = productsInBasket.stream()
                .map(product -> Order.builder()
                        .totalPrice(product.getQuantity() * product.getPrice())
                        .numberOfItems(product.getQuantity())
                        .status(OrderStatus.CREATED)
                        .user(currentUser)
                        .product(productService.getById(product.getId()))
                        .orderNumber(orderNumber)
                        .build())
                .collect(Collectors.toList());
        orderRepository.saveAll(order);
        basketService.clearBasket();
        return order;
    }

    @Override
    public List<Order> getByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber);
    }

    @Override
    public void deleteByOrderNumber(String orderNumber) {
        orderRepository.deleteByOrderNumber(orderNumber);
    }

    @Override
    public Double getTotalOrderPrice() {
        var totalPrice = 0.0;
        List<Product> productsInBasket = basketService.getProductsInBasket();
        for (Product product : productsInBasket) {
            totalPrice += product.getPrice();
        }
        return totalPrice;
    }

    @Override
    public List<Order> getOrderByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber);
    }
}
