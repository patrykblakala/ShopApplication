package com.patryk.shop.service;


import com.patryk.shop.domain.dao.Basket;
import com.patryk.shop.domain.dao.Product;

import java.util.List;

public interface BasketService {
    void addToBasket(Long productId, Integer quantity);

    void deleteProduct(Long productId);

    void clearBasket();

    List<Product> getProductsInBasket();

}
