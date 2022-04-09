package com.patryk.shop.generator.strategy;

import com.patryk.shop.generator.domain.FileType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class GeneratorsStrategy {
    @Getter

    private final FileType fileType;

    public abstract void generateFile();
}
