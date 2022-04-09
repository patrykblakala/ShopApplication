package com.patryk.shop.flyweight.strategy.generator;

import com.patryk.shop.flyweight.strategy.GenericStrategy;
import com.patryk.shop.generator.domain.FileType;

public interface StrategyGenerator extends GenericStrategy<FileType> {
    byte[] generateFile();
}
