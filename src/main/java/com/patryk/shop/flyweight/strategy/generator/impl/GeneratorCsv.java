package com.patryk.shop.flyweight.strategy.generator.impl;

import com.opencsv.CSVWriter;
import com.patryk.shop.flyweight.strategy.generator.StrategyGenerator;
import com.patryk.shop.generator.domain.FileType;
import com.patryk.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.StringWriter;

@Component
@RequiredArgsConstructor
public class GeneratorCsv implements StrategyGenerator {
    private final ProductRepository productRepository;
    @Override
    public byte[] generateFile() {
        StringWriter stringWriter = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(stringWriter);
        csvWriter.writeNext(new String[]{"Id", "Name", "Price", "Quantity"});
        productRepository.findAll().forEach(a->csvWriter.writeNext(new String[]{a.getId().toString(),
                a.getName(), a.getPrice().toString(), a.getQuantity().toString()}));
        return stringWriter.toString().getBytes();
    }

    @Override
    public FileType getType() {
        return FileType.CSV;
    }
}
