package com.patryk.shop.generator.strategy;

import com.patryk.shop.generator.domain.FileType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PdfGenerator extends GeneratorsStrategy{

    public PdfGenerator() {
        super(FileType.PDF);
    }

    @Override
    public void generateFile() {

log.info("PDF");    }
}
