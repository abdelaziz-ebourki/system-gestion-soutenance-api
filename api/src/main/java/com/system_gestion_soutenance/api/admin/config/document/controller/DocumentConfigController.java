package com.system_gestion_soutenance.api.admin.config.document.controller;

import com.system_gestion_soutenance.api.admin.config.document.entity.DocumentConfig;
import com.system_gestion_soutenance.api.admin.config.document.service.DocumentConfigService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/config/documents")
public class DocumentConfigController {

    private final DocumentConfigService service;

    public DocumentConfigController(DocumentConfigService service) {
        this.service = service;
    }

    @GetMapping
    public DocumentConfig get() {
        return service.get();
    }

    @PutMapping
    public DocumentConfig update(@RequestBody Map<String, Object> updates) {
        return service.update(updates);
    }
}
