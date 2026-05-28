package com.system_gestion_soutenance.api.admin.audit.repository;

import com.system_gestion_soutenance.api.admin.audit.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findAllByOrderByTimestampDesc();
}
