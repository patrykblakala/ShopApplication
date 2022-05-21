package com.patryk.shop.service.impl;

import com.patryk.shop.domain.NotificationStatus;
import com.patryk.shop.domain.dao.NotificationHistory;
import com.patryk.shop.domain.dao.Template;
import com.patryk.shop.service.MailService;
import com.patryk.shop.service.NotificationHistoryService;
import com.patryk.shop.service.TemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;

    private final TemplateService templateService;

    private final ITemplateEngine iTemplateEngine;

    private final NotificationHistoryService notificationHistoryService;

    @Override
    public void send(String email, String templateName, Map<String, Object> variables, byte[] file, String fileName) {

        Template template = templateService.findByName(templateName);
        Context context = new Context(Locale.getDefault(), variables);
        String htmlBody = iTemplateEngine.process(template.getBody(), context);

        NotificationHistory notificationHistory = notificationHistoryService.save(NotificationHistory.builder()
                .receiver(email)
                .body(template.getBody())
                .subject(template.getSubject())
                .notificationStatus(NotificationStatus.CREATED)
                .build());

        try {
            javaMailSender.send(message -> {
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message);
                mimeMessageHelper.setTo(email);
                mimeMessageHelper.setFrom("patrykblakala108java@gmail.com");
                mimeMessageHelper.setSubject(template.getSubject());
                mimeMessageHelper.setText(htmlBody, true);
                if (file != null) mimeMessageHelper.addAttachment(fileName, new ByteArrayResource(file));
            });
            notificationHistory.setNotificationStatus(NotificationStatus.DONE);
        } catch(Exception e) {
            notificationHistory.setNotificationStatus(NotificationStatus.ERROR);
            notificationHistory.setError(e.getMessage());
            log.error(e.getMessage(), e);
        }
        notificationHistoryService.save(notificationHistory);
    }
}
