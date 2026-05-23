package com.system_gestion_soutenance.api.teacher.evaluation.controller;

import com.system_gestion_soutenance.api.teacher.evaluation.service.EvaluationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/teacher/evaluations")
public class EvaluationController {

    private final EvaluationService evaluationService;

    public EvaluationController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @GetMapping
    public List<Map<String, Object>> findByTeacher(@RequestParam(defaultValue = "3") String teacherId) {
        return evaluationService.findByTeacher(teacherId);
    }

    @PostMapping("/{id}")
    public Map<String, Object> submit(@PathVariable String id, @RequestBody Map<String, Object> body) {
        return evaluationService.submit(id, body);
    }
}
