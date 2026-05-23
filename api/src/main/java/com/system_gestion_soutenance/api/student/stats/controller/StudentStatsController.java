package com.system_gestion_soutenance.api.student.stats.controller;

import com.system_gestion_soutenance.api.student.stats.service.StudentStatsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/student/stats")
public class StudentStatsController {

    private final StudentStatsService statsService;

    public StudentStatsController(StudentStatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping
    public Map<String, Object> getStats(@RequestParam(defaultValue = "std-demo") String studentId) {
        return statsService.getStats(studentId);
    }
}
