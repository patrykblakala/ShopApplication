package com.patryk.shop.service;

import com.patryk.shop.domain.dao.Order;

import java.util.List;

public interface OrderService {
    List<Order> save();

    List<Order> getByOrderNumber(String orderNumber);

    void deleteByOrderNumber(String orderNumber);

    Double getTotalOrderPrice();

    List<Order> getOrderByOrderNumber(String orderNumber);
}
