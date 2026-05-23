package com.system_gestion_soutenance.api.admin.config.grade.controller;

import com.system_gestion_soutenance.api.admin.config.grade.dto.CreateGradeRequest;
import com.system_gestion_soutenance.api.admin.config.grade.entity.Grade;
import com.system_gestion_soutenance.api.admin.config.grade.service.GradeConfigService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/config/grades")
public class GradeConfigController {

    private final GradeConfigService gradeConfigService;

    public GradeConfigController(GradeConfigService gradeConfigService) {
        this.gradeConfigService = gradeConfigService;
    }

    @GetMapping
    public List<Grade> findAll() {
        return gradeConfigService.findAll();
    }

    @PostMapping
    public ResponseEntity<Grade> create(@Valid @RequestBody CreateGradeRequest request) {
        Grade grade = gradeConfigService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(grade);
    }

    @PutMapping("/{id}")
    public Grade update(@PathVariable String id, @Valid @RequestBody CreateGradeRequest request) {
        return gradeConfigService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        gradeConfigService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
