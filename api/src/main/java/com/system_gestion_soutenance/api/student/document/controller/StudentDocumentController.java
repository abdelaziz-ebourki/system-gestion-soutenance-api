package com.system_gestion_soutenance.api.student.document.controller;

import com.system_gestion_soutenance.api.student.document.entity.StudentDocument;
import com.system_gestion_soutenance.api.student.document.service.StudentDocumentService;
import com.system_gestion_soutenance.api.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/student/documents")
@Tag(name = "Student - Documents", description = "Gestion des documents de soutenance")
public class StudentDocumentController {

    private final StudentDocumentService studentDocumentService;

    public StudentDocumentController(StudentDocumentService studentDocumentService) {
        this.studentDocumentService = studentDocumentService;
    }

    @GetMapping
    @Operation(summary = "List documents for the connected student")
    public List<StudentDocument> findByStudent() {
        return studentDocumentService.findByStudent(getCurrentUserId());
    }

    @PostMapping("/{id}/upload")
    @Operation(summary = "Upload a document file")
    public ResponseEntity<StudentDocument> upload(@PathVariable String id,
                                                   @RequestParam("file") MultipartFile file) {
        StudentDocument doc = studentDocumentService.upload(id, file);
        return ResponseEntity.ok(doc);
    }

    private String getCurrentUserId() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}
