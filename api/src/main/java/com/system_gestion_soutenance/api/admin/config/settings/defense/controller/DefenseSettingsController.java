package com.system_gestion_soutenance.api.admin.config.settings.defense.controller;

import com.system_gestion_soutenance.api.admin.config.settings.defense.entity.DefenseSettings;
import com.system_gestion_soutenance.api.admin.config.settings.defense.service.DefenseSettingsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/config/settings")
public class DefenseSettingsController {

    private final DefenseSettingsService service;

    public DefenseSettingsController(DefenseSettingsService service) {
        this.service = service;
    }

    @GetMapping
    public DefenseSettings get() {
        return service.get();
    }

    @PostMapping
    public DefenseSettings update(@RequestBody Map<String, Object> updates) {
        return service.update(updates);
    }
}
