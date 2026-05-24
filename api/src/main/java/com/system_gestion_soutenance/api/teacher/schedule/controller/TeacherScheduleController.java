package com.system_gestion_soutenance.api.teacher.schedule.controller;

import com.system_gestion_soutenance.api.teacher.schedule.service.TeacherScheduleService;
import com.system_gestion_soutenance.api.user.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/teacher/schedule")
public class TeacherScheduleController {

    private final TeacherScheduleService service;

    public TeacherScheduleController(TeacherScheduleService service) {
        this.service = service;
    }

    @GetMapping
    public List<Map<String, Object>> getSchedule() {
        return service.getSchedule(getCurrentUserId());
    }

    private String getCurrentUserId() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}
