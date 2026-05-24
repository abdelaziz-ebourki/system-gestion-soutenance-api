package com.system_gestion_soutenance.api.student.document.controller;

import com.system_gestion_soutenance.api.student.document.entity.StudentDocument;
import com.system_gestion_soutenance.api.student.document.service.StudentDocumentService;
import com.system_gestion_soutenance.api.user.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/student/documents")
public class StudentDocumentController {

    private final StudentDocumentService studentDocumentService;

    public StudentDocumentController(StudentDocumentService studentDocumentService) {
        this.studentDocumentService = studentDocumentService;
    }

    @GetMapping
    public List<StudentDocument> findByStudent() {
        return studentDocumentService.findByStudent(getCurrentUserId());
    }

    @PostMapping("/{id}/upload")
    public ResponseEntity<StudentDocument> upload(@PathVariable String id,
                                                   @RequestParam("file") MultipartFile file) {
        StudentDocument doc = studentDocumentService.upload(id, file);
        return ResponseEntity.ok(doc);
    }

    private String getCurrentUserId() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}
