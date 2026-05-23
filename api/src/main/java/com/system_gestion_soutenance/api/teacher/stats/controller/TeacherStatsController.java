package com.system_gestion_soutenance.api.teacher.stats.controller;

import com.system_gestion_soutenance.api.teacher.stats.service.TeacherStatsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/teacher/stats")
public class TeacherStatsController {

    private final TeacherStatsService statsService;

    public TeacherStatsController(TeacherStatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping
    public Map<String, Object> getStats(@RequestParam(defaultValue = "3") String teacherId) {
        return statsService.getStats(teacherId);
    }
}
