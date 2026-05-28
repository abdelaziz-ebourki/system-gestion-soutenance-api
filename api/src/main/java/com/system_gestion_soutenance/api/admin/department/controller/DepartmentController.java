package com.system_gestion_soutenance.api.admin.department.controller;

import com.system_gestion_soutenance.api.admin.department.dto.CreateDepartmentRequest;
import com.system_gestion_soutenance.api.admin.department.entity.Department;
import com.system_gestion_soutenance.api.admin.department.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/departments")
@Tag(name = "Admin - Departments", description = "Gestion des départements")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    @Operation(summary = "List all departments")
    public List<Department> findAll() {
        return departmentService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a department by ID")
    public Department findById(@PathVariable Long id) {
        return departmentService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Create a new department")
    public ResponseEntity<Department> create(@Valid @RequestBody CreateDepartmentRequest request) {
        Department department = departmentService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(department);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a department")
    public Department update(@PathVariable Long id, @Valid @RequestBody CreateDepartmentRequest request) {
        return departmentService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a department")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        departmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
