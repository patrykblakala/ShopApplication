package com.patryk.shop.generator.strategy;

import com.patryk.shop.generator.domain.FileType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CsvGenerator extends GeneratorsStrategy {
    public CsvGenerator(){
        super(FileType.CSV);
    }
    @Override
    public void generateFile() {

log.info("CSV");    }
}
