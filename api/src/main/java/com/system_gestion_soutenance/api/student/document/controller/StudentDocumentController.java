package com.system_gestion_soutenance.api.student.document.controller;

import com.system_gestion_soutenance.api.student.document.entity.StudentDocument;
import com.system_gestion_soutenance.api.student.document.service.StudentDocumentService;
import org.springframework.http.ResponseEntity;
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
    public List<StudentDocument> findByStudent(@RequestParam(defaultValue = "std-demo") String studentId) {
        return studentDocumentService.findByStudent(studentId);
    }

    @PostMapping("/{id}/upload")
    public ResponseEntity<StudentDocument> upload(@PathVariable String id,
                                                   @RequestParam("file") MultipartFile file,
                                                   @RequestParam(defaultValue = "std-demo") String studentId) {
        StudentDocument doc = studentDocumentService.upload(id, file);
        return ResponseEntity.ok(doc);
    }
}
