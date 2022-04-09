package com.patryk.shop.service;

import com.patryk.shop.domain.dao.Product;


public interface ProductService {

    Product save(Product product);

    Product getById(Long id);

    Product update(Product product, Long id);

    void deleteById(Long id);

}
