package com.soutenance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "audit_logs", indexes = @Index(name = "idx_audit_logs_timestamp", columnList = "timestamp"))
public class AuditLog extends BaseEntity {

    @Column(nullable = false, length = 40)
    private String action;

    @Column(nullable = false, length = 60)
    private String entity;

    @Column(name = "entity_id", nullable = false, length = 80)
    private String entityId;

    @Column(name = "admin_email", nullable = false, length = 180)
    private String adminEmail;

    @Column(nullable = false, length = 255)
    private String details;

    @Column(nullable = false)
    private OffsetDateTime timestamp;
}
