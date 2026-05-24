package com.system_gestion_soutenance.api.coordinator.grade.controller;

import com.system_gestion_soutenance.api.coordinator.grade.service.CoordinatorGradeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/coordinator/grades")
public class CoordinatorGradeController {

    private final CoordinatorGradeService gradeService;

    public CoordinatorGradeController(CoordinatorGradeService gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping
    public List<Map<String, Object>> getGrades() {
        return gradeService.getGrades();
    }
}
