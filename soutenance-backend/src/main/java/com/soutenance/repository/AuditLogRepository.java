package com.soutenance.repository;

import com.soutenance.entity.AuditLog;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, String> {
    List<AuditLog> findTop100ByOrderByTimestampDesc();
}
