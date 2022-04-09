package com.patryk.shop.processor.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCsv {

    private String name;
    private String description;
    private Double price;
    private Integer quantity;

}
