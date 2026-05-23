package com.system_gestion_soutenance.api.student.convocation.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student/convocation")
public class ConvocationController {

    @GetMapping
    public ResponseEntity<byte[]> getConvocation(@RequestParam(defaultValue = "std-demo") String studentId) {
        String placeholder = "Convocation pour l'étudiant: " + studentId
                + "\n\nCe document est un placeholder en attendant la génération PDF.";
        byte[] content = placeholder.getBytes();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "convocation.pdf");

        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }
}
