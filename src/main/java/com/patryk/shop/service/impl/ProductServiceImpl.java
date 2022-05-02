package com.patryk.shop.service.impl;

import com.patryk.shop.config.filePathConfig.FilePathPropertiesConfig;
import com.patryk.shop.domain.dao.Product;
import com.patryk.shop.repository.ProductRepository;
import com.patryk.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final FilePathPropertiesConfig filePathPropertiesConfig;

    @Override
    @Transactional
    @CachePut(value = "product", key = "#result.id")
    public Product save(Product product, MultipartFile file) {
        productRepository.save(product);
        Path path = Paths.get(filePathPropertiesConfig.getProductsPath(),
                product.getId().toString() + "." + FileNameUtils.getExtension(file.getOriginalFilename()));
        try {
            Files.copy(file.getInputStream(), path);
            product.setFilePath(path.toString());
        } catch (IOException e) {
            log.error("Couldn't save product image", e);
        }
        return product;
    }

    @Override
    @Cacheable(cacheNames = "product", key = "#id")
    public Product getById(Long id) {
        log.info("Object not in cache {}", id);
        return productRepository.getById(id);
    }

    @Override
    public Product update(Product product, MultipartFile file, Long id) {

        var productDb = getById(id);
        Path path = Paths.get(filePathPropertiesConfig.getProductsPath(),
                id + "." + FileNameUtils.getExtension(file.getOriginalFilename()));
        try {

            Path oldPath = Paths.get(productDb.getFilePath());

            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            if (!path.equals(oldPath) && Files.exists(oldPath)) {
                Files.delete(oldPath);
            }
            productDb.setFilePath(path.toString());
        } catch (IOException e) {
            log.error("Couldn't save product image", e);
        }
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
