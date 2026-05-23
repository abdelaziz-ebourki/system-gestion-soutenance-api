package com.system_gestion_soutenance.api.coordinator.jury.controller;

import com.system_gestion_soutenance.api.coordinator.jury.dto.CreateJuryRequest;
import com.system_gestion_soutenance.api.coordinator.jury.service.JuryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/coordinator/juries")
public class JuryController {

    private final JuryService juryService;

    public JuryController(JuryService juryService) {
        this.juryService = juryService;
    }

    @GetMapping
    public List<Map<String, Object>> findAll() {
        return juryService.findAll();
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody CreateJuryRequest request) {
        Map<String, Object> jury = juryService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(jury);
    }

    @PutMapping("/{id}")
    public Map<String, Object> update(@PathVariable String id, @RequestBody Map<String, Object> updates) {
        return juryService.update(id, updates);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        juryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
