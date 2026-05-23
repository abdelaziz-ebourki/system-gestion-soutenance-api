package com.system_gestion_soutenance.api.notification.controller;

import com.system_gestion_soutenance.api.notification.entity.AppNotification;
import com.system_gestion_soutenance.api.notification.repository.NotificationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationRepository repository;

    public NotificationController(NotificationRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<AppNotification> findAll() {
        return repository.findAllByOrderByTimestampDesc();
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<Void> markRead(@PathVariable String id) {
        repository.findById(id).ifPresent(n -> {
            n.setRead(true);
            repository.save(n);
        });
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/read-all")
    public ResponseEntity<Void> markAllRead() {
        List<AppNotification> all = repository.findAll();
        for (AppNotification n : all) {
            n.setRead(true);
        }
        repository.saveAll(all);
        return ResponseEntity.noContent().build();
    }
}
