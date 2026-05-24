package com.system_gestion_soutenance.api.admin.config.grade.controller;

import com.system_gestion_soutenance.api.admin.config.grade.dto.CreateGradeRequest;
import com.system_gestion_soutenance.api.admin.config.grade.entity.Grade;
import com.system_gestion_soutenance.api.admin.config.grade.service.GradeConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/config/grades")
@Tag(name = "Admin - Grades", description = "Gestion des grades")
public class GradeConfigController {

    private final GradeConfigService gradeConfigService;

    public GradeConfigController(GradeConfigService gradeConfigService) {
        this.gradeConfigService = gradeConfigService;
    }

    @GetMapping
    @Operation(summary = "List all grades")
    public List<Grade> findAll() {
        return gradeConfigService.findAll();
    }

    @PostMapping
    @Operation(summary = "Create a new grade")
    public ResponseEntity<Grade> create(@Valid @RequestBody CreateGradeRequest request) {
        Grade grade = gradeConfigService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(grade);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a grade")
    public Grade update(@PathVariable String id, @Valid @RequestBody CreateGradeRequest request) {
        return gradeConfigService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a grade")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        gradeConfigService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
