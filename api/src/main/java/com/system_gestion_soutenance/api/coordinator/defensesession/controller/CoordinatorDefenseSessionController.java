package com.system_gestion_soutenance.api.coordinator.defensesession.controller;

import com.system_gestion_soutenance.api.admin.defensesession.entity.DefenseSession;
import com.system_gestion_soutenance.api.coordinator.defensesession.service.CoordinatorDefenseSessionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/coordinator/defense-sessions")
public class CoordinatorDefenseSessionController {

    private final CoordinatorDefenseSessionService service;

    public CoordinatorDefenseSessionController(CoordinatorDefenseSessionService service) {
        this.service = service;
    }

    @GetMapping
    public List<DefenseSession> findAll() {
        return service.findAll();
    }

    @PostMapping("/{id}/transition")
    public DefenseSession transition(@PathVariable String id, @RequestBody Map<String, String> body) {
        return service.transition(id, body.get("toStatus"));
    }
}
