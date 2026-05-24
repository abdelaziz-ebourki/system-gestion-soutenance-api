package com.system_gestion_soutenance.api.coordinator.defense.controller;

import com.system_gestion_soutenance.api.coordinator.schedule.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/coordinator/defenses")
public class CoordinatorDefenseController {

    private final ScheduleService scheduleService;

    public CoordinatorDefenseController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Map<String, String>> cancel(@PathVariable String id) {
        scheduleService.cancelDefense(id);
        return ResponseEntity.ok(Map.of("message", "Soutenance annulée."));
    }
}
