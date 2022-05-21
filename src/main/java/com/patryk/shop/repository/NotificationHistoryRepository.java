package com.patryk.shop.repository;

import com.patryk.shop.domain.NotificationStatus;
import com.patryk.shop.domain.dao.NotificationHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationHistoryRepository extends JpaRepository<NotificationHistory, Long> {

    Page<NotificationHistory> findByNotificationStatus(NotificationStatus notificationStatus, Pageable pageable);
}
