package com.system_gestion_soutenance.api.student.group.controller;

import com.system_gestion_soutenance.api.student.group.service.StudentGroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public Map<String, Object> getWorkspace(@RequestParam(defaultValue = "std-demo") String studentId) {
        return studentGroupService.getWorkspace(studentId);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createGroup(@RequestParam(defaultValue = "std-demo") String studentId) {
        Map<String, Object> group = studentGroupService.createGroup(studentId);
        return ResponseEntity.status(HttpStatus.CREATED).body(group);
    }

    @PostMapping("/{id}/join")
    public Map<String, Object> joinGroup(@PathVariable String id,
                                          @RequestParam(defaultValue = "std-demo") String studentId) {
        return studentGroupService.joinGroup(id, studentId);
    }
}
