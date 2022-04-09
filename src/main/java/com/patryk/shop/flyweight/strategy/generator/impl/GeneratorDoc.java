package com.patryk.shop.flyweight.strategy.generator.impl;

import com.patryk.shop.flyweight.strategy.generator.StrategyGenerator;
import com.patryk.shop.generator.domain.FileType;
import org.springframework.stereotype.Component;

@Component
public class GeneratorDoc implements StrategyGenerator {
    @Override
    public byte[] generateFile() {

        return new byte[0];
    }

    @Override
    public FileType getType() {
        return FileType.DOC;
    }
}
