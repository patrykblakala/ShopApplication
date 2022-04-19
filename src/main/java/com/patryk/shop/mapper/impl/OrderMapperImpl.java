package com.patryk.shop.mapper.impl;

import com.patryk.shop.domain.dao.Order;
import com.patryk.shop.domain.dto.OrderDto;
import com.patryk.shop.mapper.OrderMapper;
import com.patryk.shop.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderMapperImpl implements OrderMapper {

    private final ProductMapper productMapper;

    @Override
    public OrderDto orderToOrderDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .createdDate(order.getCreatedDate())
                .status(order.getStatus())
                .numberOfItems(order.getNumberOfItems())
                .totalPrice(order.getTotalPrice())
                .build();
    }

    @Override
    public OrderDto orderToOrderDtos(List<Order> orders) {
        return OrderDto.builder()
                .createdDate(orders.get(0).getCreatedDate())
                .status(orders.get(0).getStatus())
                .numberOfItems(orders.size())
                .totalPrice(orders.stream()
                        .mapToDouble(Order::getTotalPrice)
                        .sum())
                .products(orders.stream()
                        .map(order -> productMapper.productDaoToProductDto(order.getProduct()))
                        .collect(Collectors.toList()))
                .build();
    }
}
