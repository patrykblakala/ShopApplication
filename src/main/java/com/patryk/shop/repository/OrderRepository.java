package com.patryk.shop.repository;

import com.patryk.shop.domain.dao.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, RevisionRepository<Order, Long, Integer> {
    List<Order> findByOrderNumber(String orderNumber);

    void deleteByOrderNumber(String orderNumber);


}
