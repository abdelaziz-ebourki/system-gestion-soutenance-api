package com.system_gestion_soutenance.api.student.group.controller;

import com.system_gestion_soutenance.api.student.group.service.StudentGroupService;
import com.system_gestion_soutenance.api.user.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/student/group")
public class StudentGroupController {

    private final StudentGroupService studentGroupService;

    public StudentGroupController(StudentGroupService studentGroupService) {
        this.studentGroupService = studentGroupService;
    }

    @GetMapping
    public Map<String, Object> getWorkspace() {
        return studentGroupService.getWorkspace(getCurrentUserId());
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createGroup() {
        Map<String, Object> group = studentGroupService.createGroup(getCurrentUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(group);
    }

    @PostMapping("/{id}/join")
    public Map<String, Object> joinGroup(@PathVariable String id) {
        return studentGroupService.joinGroup(id, getCurrentUserId());
    }

    private String getCurrentUserId() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}
