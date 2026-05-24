package com.system_gestion_soutenance.api.teacher.stats.controller;

import com.system_gestion_soutenance.api.teacher.stats.service.TeacherStatsService;
import com.system_gestion_soutenance.api.user.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public Map<String, Object> getStats() {
        return statsService.getStats(getCurrentUserId());
    }

    private String getCurrentUserId() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}
