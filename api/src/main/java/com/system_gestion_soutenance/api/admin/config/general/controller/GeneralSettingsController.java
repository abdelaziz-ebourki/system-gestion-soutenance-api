package com.system_gestion_soutenance.api.admin.config.general.controller;

import com.system_gestion_soutenance.api.admin.config.general.entity.GeneralSettings;
import com.system_gestion_soutenance.api.admin.config.general.service.GeneralSettingsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/config/general")
public class GeneralSettingsController {

    private final GeneralSettingsService service;

    public GeneralSettingsController(GeneralSettingsService service) {
        this.service = service;
    }

    @GetMapping
    public GeneralSettings get() {
        return service.get();
    }

    @PutMapping
    public GeneralSettings update(@RequestBody Map<String, Object> updates) {
        return service.update(updates);
    }
}
