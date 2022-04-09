package com.patryk.shop.generator.strategy;

import com.patryk.shop.generator.domain.FileType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JsonGenerator extends GeneratorsStrategy {
    public JsonGenerator() {
        super(FileType.JSON);
    }

    @Override
    public void generateFile() {

log.info("JSON");    }
}
