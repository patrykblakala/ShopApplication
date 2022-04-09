package com.patryk.shop.generator.strategy;

import com.patryk.shop.generator.domain.FileType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class XlsGenerator extends GeneratorsStrategy {

    public XlsGenerator(){
        super(FileType.XLS);
    }
    @Override
    public void generateFile() {
log.info("XLS");    }
}
