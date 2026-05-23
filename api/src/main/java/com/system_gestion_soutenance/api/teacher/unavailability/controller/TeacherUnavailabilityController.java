package com.system_gestion_soutenance.api.teacher.unavailability.controller;

import com.system_gestion_soutenance.api.teacher.unavailability.service.TeacherUnavailabilityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/teacher/unavailability")
public class TeacherUnavailabilityController {

    private final TeacherUnavailabilityService service;

    public TeacherUnavailabilityController(TeacherUnavailabilityService service) {
        this.service = service;
    }

    @GetMapping
    public Map<String, Object> get(@RequestParam(defaultValue = "3") String teacherId) {
        return service.getByTeacher(teacherId);
    }

    @SuppressWarnings("unchecked")
    @PostMapping
    public Map<String, Object> save(@RequestParam(defaultValue = "3") String teacherId,
                                     @RequestBody Map<String, Object> body) {
        Map<String, List<String>> slotsByDate;
        if (body.get("slotsByDate") != null) {
            slotsByDate = (Map<String, List<String>>) body.get("slotsByDate");
        } else {
            slotsByDate = (Map<String, List<String>>) (Map) body;
        }
        return service.saveForTeacher(teacherId, slotsByDate);
    }
}
