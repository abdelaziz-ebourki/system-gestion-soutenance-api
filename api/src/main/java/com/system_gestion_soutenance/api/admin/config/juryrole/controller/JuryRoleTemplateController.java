package com.system_gestion_soutenance.api.admin.config.juryrole.controller;

import com.system_gestion_soutenance.api.admin.config.juryrole.dto.CreateJuryRoleTemplateRequest;
import com.system_gestion_soutenance.api.admin.config.juryrole.entity.JuryRoleTemplate;
import com.system_gestion_soutenance.api.admin.config.juryrole.service.JuryRoleTemplateService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/config/jury-role-templates")
public class JuryRoleTemplateController {

    private final JuryRoleTemplateService juryRoleTemplateService;

    public JuryRoleTemplateController(JuryRoleTemplateService juryRoleTemplateService) {
        this.juryRoleTemplateService = juryRoleTemplateService;
    }

    @GetMapping
    public List<JuryRoleTemplate> findAll() {
        return juryRoleTemplateService.findAll();
    }

    @PostMapping
    public ResponseEntity<JuryRoleTemplate> create(@Valid @RequestBody CreateJuryRoleTemplateRequest request) {
        JuryRoleTemplate template = juryRoleTemplateService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(template);
    }

    @PutMapping("/{id}")
    public JuryRoleTemplate update(@PathVariable String id, @Valid @RequestBody CreateJuryRoleTemplateRequest request) {
        return juryRoleTemplateService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        juryRoleTemplateService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
