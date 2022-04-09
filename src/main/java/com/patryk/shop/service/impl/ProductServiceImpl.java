package com.patryk.shop.service.impl;

import com.patryk.shop.domain.dao.Product;
import com.patryk.shop.repository.ProductRepository;
import com.patryk.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @CachePut(value = "product", key = "#result.id")
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    @Cacheable(cacheNames = "product", key = "#id")
    public Product getById(Long id) {
        log.info("Object not in cache {}", id);
        return productRepository.getById(id);
    }

    @Override
    public Product update(Product product, Long id) {
        var productDb = getById(id);
        productDb.setName(product.getName());
        productDb.setPrice(product.getPrice());
        productDb.setQuantity(product.getQuantity());
        return productRepository.save(productDb);
    }

    @Override

    @CacheEvict(cacheNames = "product", key = "#id")
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

}
