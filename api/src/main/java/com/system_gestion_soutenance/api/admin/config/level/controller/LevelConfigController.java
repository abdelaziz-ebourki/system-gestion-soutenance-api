package com.system_gestion_soutenance.api.admin.config.level.controller;

import com.system_gestion_soutenance.api.admin.config.level.dto.CreateLevelRequest;
import com.system_gestion_soutenance.api.admin.config.level.entity.Level;
import com.system_gestion_soutenance.api.admin.config.level.service.LevelConfigService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/config/levels")
public class LevelConfigController {

    private final LevelConfigService levelConfigService;

    public LevelConfigController(LevelConfigService levelConfigService) {
        this.levelConfigService = levelConfigService;
    }

    @GetMapping
    public List<Level> findAll() {
        return levelConfigService.findAll();
    }

    @PostMapping
    public ResponseEntity<Level> create(@Valid @RequestBody CreateLevelRequest request) {
        Level level = levelConfigService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(level);
    }

    @PutMapping("/{id}")
    public Level update(@PathVariable String id, @Valid @RequestBody CreateLevelRequest request) {
        return levelConfigService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        levelConfigService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
