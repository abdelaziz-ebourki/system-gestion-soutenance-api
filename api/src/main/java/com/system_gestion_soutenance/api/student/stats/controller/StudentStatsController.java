package com.system_gestion_soutenance.api.student.stats.controller;

import com.system_gestion_soutenance.api.student.stats.service.StudentStatsService;
import com.system_gestion_soutenance.api.user.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public Map<String, Object> getStats() {
        return statsService.getStats(getCurrentUserId());
    }

    private String getCurrentUserId() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}
