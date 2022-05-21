package com.patryk.shop.service.impl;

import com.patryk.shop.domain.NotificationStatus;
import com.patryk.shop.domain.dao.NotificationHistory;
import com.patryk.shop.repository.NotificationHistoryRepository;
import com.patryk.shop.service.NotificationHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationHistoryServiceImpl implements NotificationHistoryService {

    private final NotificationHistoryRepository notificationHistoryRepository;


    @Override
    public NotificationHistory save(NotificationHistory notificationHistory) {
        return notificationHistoryRepository.save(notificationHistory);
    }

    @Override
    public Page<NotificationHistory> getNotificationsByStatus(NotificationStatus notificationStatus, Pageable pageable) {
        return notificationHistoryRepository.findByNotificationStatus(notificationStatus, pageable);
    }
}
