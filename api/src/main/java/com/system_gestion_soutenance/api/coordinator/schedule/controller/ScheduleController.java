package com.system_gestion_soutenance.api.coordinator.schedule.controller;

import com.system_gestion_soutenance.api.coordinator.schedule.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/coordinator/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping
    public Map<String, Map<String, Object>> get() {
        return scheduleService.getSchedule();
    }

    @SuppressWarnings("unchecked")
    @PostMapping
    public Map<String, Map<String, Object>> save(@RequestBody Map<String, Object> body) {
        Object raw = body.get("schedule");
        if (raw == null) {
            throw new IllegalArgumentException("Le champ 'schedule' est requis");
        }
        Map<String, Map<String, Object>> schedule = (Map<String, Map<String, Object>>) raw;
        return scheduleService.saveSchedule(schedule);
    }
}
