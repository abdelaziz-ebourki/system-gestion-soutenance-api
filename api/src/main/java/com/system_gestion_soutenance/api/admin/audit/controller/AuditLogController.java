package com.system_gestion_soutenance.api.admin.audit.controller;

import com.system_gestion_soutenance.api.admin.audit.dto.AuditLogRequest;
import com.system_gestion_soutenance.api.admin.audit.entity.AuditLog;
import com.system_gestion_soutenance.api.admin.audit.repository.AuditLogRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/admin/audit-logs")
@Tag(name = "Admin - Audit Logs", description = "Journal d'audit")
public class AuditLogController {

    private final AuditLogRepository repository;

    public AuditLogController(AuditLogRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Operation(summary = "List all audit logs")
    public List<AuditLog> findAll() {
        return repository.findAllByOrderByTimestampDesc();
    }

    @PostMapping
    @Operation(summary = "Create an audit log entry")
    public ResponseEntity<AuditLog> create(@Valid @RequestBody AuditLogRequest request) {
        AuditLog log = new AuditLog();
        log.setAction(request.action());
        log.setEntity(request.entity());
        log.setEntityId(request.entityId());
        log.setAdminEmail(request.adminEmail());
        log.setDetails(request.details());
        log.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(log));
    }
}
