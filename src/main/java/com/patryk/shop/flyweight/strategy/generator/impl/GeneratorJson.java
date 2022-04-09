package com.patryk.shop.flyweight.strategy.generator.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.patryk.shop.flyweight.strategy.generator.StrategyGenerator;
import com.patryk.shop.generator.domain.FileType;
import com.patryk.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GeneratorJson implements StrategyGenerator {

    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;
    @Override
    public byte[] generateFile() {
        try {
            return objectMapper.writeValueAsBytes(productRepository.findAll());
        } catch (JsonProcessingException e) {
           log.error(e.getMessage(),e) ;
        }
        return new byte[0];
    }

    @Override
    public FileType getType() {
        return FileType.JSON;
    }
}
