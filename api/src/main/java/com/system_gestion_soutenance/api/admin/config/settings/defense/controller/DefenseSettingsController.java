package com.system_gestion_soutenance.api.admin.config.settings.defense.controller;

import com.system_gestion_soutenance.api.admin.config.settings.defense.entity.DefenseSettings;
import com.system_gestion_soutenance.api.admin.config.settings.defense.service.DefenseSettingsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/config/settings")
@Tag(name = "Admin - Defense Settings", description = "Paramètres des soutenances")
public class DefenseSettingsController {

    private final DefenseSettingsService service;

    public DefenseSettingsController(DefenseSettingsService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get defense settings")
    public DefenseSettings get() {
        return service.get();
    }

    @PostMapping
    @Operation(summary = "Update defense settings")
    public DefenseSettings update(@RequestBody Map<String, Object> updates) {
        return service.update(updates);
    }
}
