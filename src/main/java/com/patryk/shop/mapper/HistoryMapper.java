package com.patryk.shop.mapper;

import com.patryk.shop.domain.dao.Order;
import com.patryk.shop.domain.dao.Product;
import com.patryk.shop.domain.dao.User;
import com.patryk.shop.domain.dto.OrderDto;
import com.patryk.shop.domain.dto.ProductDto;
import com.patryk.shop.domain.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.history.Revision;

@Mapper(componentModel = "spring")
public interface HistoryMapper {
    @Mapping(source = "entity.id", target = "id")
    @Mapping(source = "entity.email", target = "email")
    @Mapping(source = "entity.password", target = "password")
    @Mapping(source = "entity.version", target = "version")
    @Mapping(source = "metadata.revisionType", target = "operationType")
    @Mapping(source = "requiredRevisionNumber", target = "revisionNumber")
    UserDto toUserDto(Revision<Integer, User> revision);
    @Mapping(source = "entity.id", target = "id")
    @Mapping(source = "entity.name", target = "name")
    @Mapping(source = "entity.price", target = "price")
    @Mapping(source = "entity.quantity", target = "quantity")
    @Mapping(source = "entity.version", target = "version")
    @Mapping(source = "metadata.revisionType", target = "operationType")
    @Mapping(source = "requiredRevisionNumber", target = "revisionNumber")
    ProductDto toProductDto(Revision<Integer, Product> revision);
    @Mapping(source = "entity.id", target = "id")
    @Mapping(source = "entity.createdDate", target = "createdDate")
    @Mapping(source = "entity.status", target = "status")
    @Mapping(source = "entity.numberOfItems", target = "numberOfItems")
    @Mapping(source = "entity.totalPrice", target = "totalPrice")
    @Mapping(source = "entity.orderNumber", target = "orderNumber")
    OrderDto toOrderDto(Revision<Integer, Order> revision);
}
