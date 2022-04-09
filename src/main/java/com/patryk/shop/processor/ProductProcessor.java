package com.patryk.shop.processor;

import com.patryk.shop.domain.dao.Product;
import com.patryk.shop.processor.domain.ProductCsv;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ProductProcessor implements ItemProcessor<ProductCsv, Product> {
    @Override
    public Product process(ProductCsv productCsv) throws Exception {
        return Product.builder()
                .name(productCsv.getName())
                .price(productCsv.getPrice())
                .quantity(productCsv.getQuantity())
                .build();
    }
}
