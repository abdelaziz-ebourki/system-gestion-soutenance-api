package com.soutenance.repository;

import com.soutenance.entity.Filiere;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FiliereRepository extends JpaRepository<Filiere, String> {
    Optional<Filiere> findByNameIgnoreCase(String name);
}
