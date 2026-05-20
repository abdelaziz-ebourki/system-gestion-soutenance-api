package com.soutenance.repository;

import com.soutenance.entity.Session;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, String> {
    long countByStatus(String status);
    List<Session> findByStatusOrderByStartDateAsc(String status);
}
