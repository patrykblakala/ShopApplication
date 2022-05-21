package com.patryk.shop.controller;

import com.patryk.shop.domain.NotificationStatus;
import com.patryk.shop.domain.dao.NotificationHistory;
import com.patryk.shop.service.NotificationHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/notifications", produces = MediaType.APPLICATION_JSON_VALUE)
public class NotificationHistoryController {

    private final NotificationHistoryService notificationHistoryService;

    @GetMapping
    public Page<NotificationHistory> getNotificationsByStatus(@RequestParam NotificationStatus notificationStatus, @RequestParam int page, @RequestParam int size) {
        return notificationHistoryService.getNotificationsByStatus(notificationStatus, PageRequest.of(page, size));
    }
}
