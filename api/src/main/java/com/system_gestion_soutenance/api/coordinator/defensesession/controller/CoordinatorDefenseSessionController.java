package com.system_gestion_soutenance.api.coordinator.defensesession.controller;

import com.system_gestion_soutenance.api.admin.defensesession.entity.DefenseSession;
import com.system_gestion_soutenance.api.coordinator.defensesession.service.CoordinatorDefenseSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/coordinator/defense-sessions")
@Tag(name = "Coordinator - Defense Sessions", description = "Gestion des sessions de soutenance")
public class CoordinatorDefenseSessionController {

    private final CoordinatorDefenseSessionService service;

    public CoordinatorDefenseSessionController(CoordinatorDefenseSessionService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "List all defense sessions")
    public List<DefenseSession> findAll() {
        return service.findAll();
    }

    @PostMapping("/{id}/transition")
    @Operation(summary = "Transition a defense session to a new status")
    public DefenseSession transition(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return service.transition(id, body.get("toStatus"));
    }
}
