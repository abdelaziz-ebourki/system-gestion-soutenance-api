package com.system_gestion_soutenance.api.admin.config.defensetype.controller;

import com.system_gestion_soutenance.api.admin.config.defensetype.service.DefenseTypeConfigService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/config/defense-types")
public class DefenseTypeConfigController {

    private final DefenseTypeConfigService service;

    public DefenseTypeConfigController(DefenseTypeConfigService service) {
        this.service = service;
    }

    @GetMapping
    public Map<String, Map<String, Object>> get() {
        return service.getAllGrouped();
    }

    @PutMapping
    public Map<String, Map<String, Object>> update(@RequestBody Map<String, Map<String, Object>> updates) {
        return service.updateGrouped(updates);
    }
}
