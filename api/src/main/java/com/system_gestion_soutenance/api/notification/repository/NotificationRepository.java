package com.system_gestion_soutenance.api.notification.repository;

import com.system_gestion_soutenance.api.notification.entity.AppNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<AppNotification, Long> {
    List<AppNotification> findAllByOrderByTimestampDesc();
}
