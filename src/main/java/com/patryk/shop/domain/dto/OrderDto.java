package com.patryk.shop.domain.dto;

import com.patryk.shop.domain.dao.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {

    private Long id;
    private LocalDate createdDate;
    private OrderStatus status;
    private Integer numberOfItems;
    private Double totalPrice;
    private String orderNumber;
    private List<ProductDto> products;


}
