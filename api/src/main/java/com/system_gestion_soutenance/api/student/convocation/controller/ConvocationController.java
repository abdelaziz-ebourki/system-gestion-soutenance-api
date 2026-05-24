package com.system_gestion_soutenance.api.student.convocation.controller;

import com.system_gestion_soutenance.api.student.defense.service.StudentDefenseService;
import com.system_gestion_soutenance.api.user.entity.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/student/convocation")
public class ConvocationController {

    private final StudentDefenseService studentDefenseService;

    public ConvocationController(StudentDefenseService studentDefenseService) {
        this.studentDefenseService = studentDefenseService;
    }

    @GetMapping
    public ResponseEntity<byte[]> getConvocation() {
        String studentId = getCurrentUserId();

        Map<String, Object> defense;
        try {
            defense = studentDefenseService.getDefense(studentId);
        } catch (ResponseStatusException e) {
            return ResponseEntity.notFound().build();
        }

        if (!"scheduled".equals(defense.get("status"))) {
            return ResponseEntity.notFound().build();
        }

        String placeholder = "Convocation pour l'étudiant: " + studentId
                + "\n\nCe document est un placeholder en attendant la génération PDF.";
        byte[] content = placeholder.getBytes();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "convocation.pdf");

        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }

    private String getCurrentUserId() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}
