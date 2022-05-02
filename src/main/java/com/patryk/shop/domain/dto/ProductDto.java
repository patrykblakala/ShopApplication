package com.patryk.shop.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.history.RevisionMetadata;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ProductDto extends AuditableDto {

    private Long id;
    private String name;
    private Double price;
    private Integer quantity;
    private String filePath;
    private Integer revisionNumber;
    private RevisionMetadata.RevisionType operationType;

}
