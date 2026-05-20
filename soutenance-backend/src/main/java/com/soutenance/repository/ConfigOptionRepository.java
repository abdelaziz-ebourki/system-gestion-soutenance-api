package com.soutenance.repository;

import com.soutenance.entity.ConfigOption;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigOptionRepository extends JpaRepository<ConfigOption, String> {
    List<ConfigOption> findByCategoryOrderByNameAsc(String category);
    Optional<ConfigOption> findByCategoryAndNameIgnoreCase(String category, String name);
}
