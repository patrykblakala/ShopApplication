package com.patryk.shop.flyweight.strategy.mail.impl;

import com.patryk.shop.flyweight.strategy.mail.MailStrategyGenerator;
import com.patryk.shop.generator.domain.MailType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GeneratorImportantMail implements MailStrategyGenerator {
    @Override
    public MailType getType() {
        return MailType.IMPORTANT;
    }

    @Override
    public void generateMail() {
        log.info("Important Mail");
    }
}
