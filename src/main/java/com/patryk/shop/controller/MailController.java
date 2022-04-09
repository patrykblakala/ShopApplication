package com.patryk.shop.controller;

import com.patryk.shop.flyweight.GenericFactory;
import com.patryk.shop.flyweight.strategy.mail.MailStrategyGenerator;
import com.patryk.shop.generator.domain.MailType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mail")
@RequiredArgsConstructor
public class MailController {

    private final GenericFactory<MailType, MailStrategyGenerator> genericFactory;

    @GetMapping
    public void getMail(@RequestParam MailType mailType) {
        genericFactory.getByKey(mailType).generateMail();
    }
}
