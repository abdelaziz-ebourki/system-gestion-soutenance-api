package com.system_gestion_soutenance.api.admin.audit.controller;

import com.system_gestion_soutenance.api.admin.audit.entity.AuditLog;
import com.system_gestion_soutenance.api.admin.audit.repository.AuditLogRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/audit-logs")
public class AuditLogController {

    private final AuditLogRepository repository;

    public AuditLogController(AuditLogRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<AuditLog> findAll() {
        return repository.findAllByOrderByTimestampDesc();
    }

    @PostMapping
    public ResponseEntity<AuditLog> create(@RequestBody Map<String, String> body) {
        AuditLog log = new AuditLog();
        log.setId(UUID.randomUUID().toString());
        log.setAction(body.get("action"));
        log.setEntity(body.get("entity"));
        log.setEntityId(body.get("entityId"));
        log.setAdminEmail(body.get("adminEmail"));
        log.setDetails(body.get("details"));
        log.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(log));
    }
}
