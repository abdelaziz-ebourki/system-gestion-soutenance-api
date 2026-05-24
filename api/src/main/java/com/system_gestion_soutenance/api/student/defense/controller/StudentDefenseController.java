package com.system_gestion_soutenance.api.student.defense.controller;

import com.system_gestion_soutenance.api.student.defense.service.StudentDefenseService;
import com.system_gestion_soutenance.api.user.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/student/defense")
public class StudentDefenseController {

    private final StudentDefenseService studentDefenseService;

    public StudentDefenseController(StudentDefenseService studentDefenseService) {
        this.studentDefenseService = studentDefenseService;
    }

    @GetMapping
    public Map<String, Object> getDefense() {
        return studentDefenseService.getDefense(getCurrentUserId());
    }

    private String getCurrentUserId() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}
