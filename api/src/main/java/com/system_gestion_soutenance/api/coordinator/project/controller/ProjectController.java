package com.system_gestion_soutenance.api.coordinator.project.controller;

import com.system_gestion_soutenance.api.coordinator.project.dto.CreateProjectRequest;
import com.system_gestion_soutenance.api.coordinator.project.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/coordinator/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public List<Map<String, Object>> findAll() {
        return projectService.findAll();
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody CreateProjectRequest request) {
        Map<String, Object> project = projectService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(project);
    }

    @PutMapping("/{id}")
    public Map<String, Object> update(@PathVariable String id, @RequestBody Map<String, Object> updates) {
        return projectService.update(id, updates);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
