package com.system_gestion_soutenance.api.teacher.schedule.controller;

import com.system_gestion_soutenance.api.teacher.schedule.service.TeacherScheduleService;
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
    public List<Map<String, Object>> getSchedule(@RequestParam(defaultValue = "3") String teacherId) {
        return service.getSchedule(teacherId);
    }
}
