package com.soutenance.repository;

import com.soutenance.entity.Level;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LevelRepository extends JpaRepository<Level, String> {
    Optional<Level> findByNameIgnoreCase(String name);
}
