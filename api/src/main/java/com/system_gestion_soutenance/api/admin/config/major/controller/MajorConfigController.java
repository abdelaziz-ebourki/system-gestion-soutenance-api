package com.system_gestion_soutenance.api.admin.config.major.controller;

import com.system_gestion_soutenance.api.admin.config.major.dto.CreateMajorRequest;
import com.system_gestion_soutenance.api.admin.config.major.entity.Major;
import com.system_gestion_soutenance.api.admin.config.major.service.MajorConfigService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/config/majors")
public class MajorConfigController {

    private final MajorConfigService majorConfigService;

    public MajorConfigController(MajorConfigService majorConfigService) {
        this.majorConfigService = majorConfigService;
    }

    @GetMapping
    public List<Major> findAll() {
        return majorConfigService.findAll();
    }

    @PostMapping
    public ResponseEntity<Major> create(@Valid @RequestBody CreateMajorRequest request) {
        Major major = majorConfigService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(major);
    }

    @PutMapping("/{id}")
    public Major update(@PathVariable String id, @Valid @RequestBody CreateMajorRequest request) {
        return majorConfigService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        majorConfigService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
