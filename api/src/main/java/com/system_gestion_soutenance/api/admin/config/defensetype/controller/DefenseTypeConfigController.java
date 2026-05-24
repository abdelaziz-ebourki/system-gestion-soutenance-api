package com.system_gestion_soutenance.api.admin.config.defensetype.controller;

import com.system_gestion_soutenance.api.admin.config.defensetype.service.DefenseTypeConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/config/defense-types")
@Tag(name = "Admin - Defense Types", description = "Configuration des types de soutenance")
public class DefenseTypeConfigController {

    private final DefenseTypeConfigService service;

    public DefenseTypeConfigController(DefenseTypeConfigService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get all defense type configurations")
    public Map<String, Map<String, Object>> get() {
        return service.getAllGrouped();
    }

    @PutMapping
    @Operation(summary = "Update defense type configurations")
    public Map<String, Map<String, Object>> update(@RequestBody Map<String, Map<String, Object>> updates) {
        return service.updateGrouped(updates);
    }
}
