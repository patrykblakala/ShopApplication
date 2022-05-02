package com.patryk.shop.service;

import com.patryk.shop.domain.dao.Product;
import org.springframework.web.multipart.MultipartFile;


public interface ProductService {

    Product save(Product product, MultipartFile file);

    Product getById(Long id);

    Product update(Product product, MultipartFile file, Long id);

    void deleteById(Long id);

}
