package com.patryk.shop.processor;

import com.patryk.shop.processor.domain.ProductCsv;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

@Component
public class ProductReader {

    @StepScope
    public ItemReader<ProductCsv> read(String filePath) {
        BeanWrapperFieldSetMapper<ProductCsv> mapper = new BeanWrapperFieldSetMapper<>();
        mapper.setTargetType(ProductCsv.class);
        return new FlatFileItemReaderBuilder<ProductCsv>()
                .name("Product Csv Reader")
                .linesToSkip(1)
                .resource(new FileSystemResource(filePath))
                .delimited()
                .names("Name", "Description", "Price", "Quantity")
                .fieldSetMapper(mapper)
                .build();
    }
}
