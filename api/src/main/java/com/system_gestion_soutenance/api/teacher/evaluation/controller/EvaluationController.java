package com.system_gestion_soutenance.api.teacher.evaluation.controller;

import com.system_gestion_soutenance.api.teacher.evaluation.dto.EvaluationSubmitRequest;
import com.system_gestion_soutenance.api.teacher.evaluation.service.EvaluationService;
import com.system_gestion_soutenance.api.user.entity.User;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public List<Map<String, Object>> findByTeacher() {
        return evaluationService.findByTeacher(getCurrentUserId());
    }

    @PostMapping("/{id}")
    public Map<String, Object> submit(@PathVariable String id,
                                       @Valid @RequestBody EvaluationSubmitRequest request) {
        return evaluationService.submit(id, request);
    }

    private String getCurrentUserId() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}
