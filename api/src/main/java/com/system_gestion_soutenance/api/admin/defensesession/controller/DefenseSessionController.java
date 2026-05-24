package com.system_gestion_soutenance.api.admin.defensesession.controller;

import com.system_gestion_soutenance.api.admin.defensesession.dto.CreateDefenseSessionRequest;
import com.system_gestion_soutenance.api.admin.defensesession.entity.DefenseSession;
import com.system_gestion_soutenance.api.admin.defensesession.service.DefenseSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/defense-sessions")
@Tag(name = "Admin - Defense Sessions", description = "Gestion des sessions de soutenance")
public class DefenseSessionController {

    private final DefenseSessionService defenseSessionService;

    public DefenseSessionController(DefenseSessionService defenseSessionService) {
        this.defenseSessionService = defenseSessionService;
    }

    @GetMapping
    @Operation(summary = "List all defense sessions")
    public List<DefenseSession> findAll() {
        return defenseSessionService.findAll();
    }

    @PostMapping
    @Operation(summary = "Create a new defense session")
    public ResponseEntity<DefenseSession> create(@Valid @RequestBody CreateDefenseSessionRequest request) {
        DefenseSession ds = defenseSessionService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ds);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a defense session")
    public DefenseSession update(@PathVariable String id, @Valid @RequestBody CreateDefenseSessionRequest request) {
        return defenseSessionService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a defense session")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        defenseSessionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
