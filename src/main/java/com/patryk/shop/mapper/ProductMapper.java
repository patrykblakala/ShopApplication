package com.patryk.shop.mapper;

import com.patryk.shop.domain.dao.Product;
import com.patryk.shop.domain.dto.ProductDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper extends AuditableMapper<Product, ProductDto> {
    ProductDto productDaoToProductDto(Product product);

    Product productDtoToProduct(ProductDto productDto);

    List<ProductDto> productsListDto(List<Product> productList);
}
