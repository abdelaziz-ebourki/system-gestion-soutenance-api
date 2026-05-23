package com.system_gestion_soutenance.api.coordinator.stats.controller;

import com.system_gestion_soutenance.api.coordinator.stats.service.CoordinatorStatsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/coordinator/stats")
public class CoordinatorStatsController {

    private final CoordinatorStatsService statsService;

    public CoordinatorStatsController(CoordinatorStatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping
    public Map<String, Object> getStats() {
        return statsService.getStats();
    }
}
