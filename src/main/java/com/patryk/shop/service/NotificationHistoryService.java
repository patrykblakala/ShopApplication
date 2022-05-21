package com.patryk.shop.service;

import com.patryk.shop.domain.NotificationStatus;
import com.patryk.shop.domain.dao.NotificationHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationHistoryService {

    NotificationHistory save(NotificationHistory notificationHistory);

    Page<NotificationHistory> getNotificationsByStatus(NotificationStatus notificationStatus, Pageable pageable);
}
