package com.patryk.shop.flyweight.strategy.mail;

import com.patryk.shop.flyweight.strategy.GenericStrategy;
import com.patryk.shop.generator.domain.MailType;

public interface MailStrategyGenerator extends GenericStrategy<MailType> {
    void generateMail();
}
