package com.patryk.shop.mapper;

import com.patryk.shop.domain.dao.Order;
import com.patryk.shop.domain.dto.OrderDto;

import java.util.List;

public interface OrderMapper {
    OrderDto orderToOrderDto(Order order);

    OrderDto orderToOrderDtos(List<Order> orders);
}

