package com.system_gestion_soutenance.api.admin.session.repository;

import com.system_gestion_soutenance.api.admin.session.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {
}
