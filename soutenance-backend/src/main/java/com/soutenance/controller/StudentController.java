package com.soutenance.controller;

import com.soutenance.dto.Dtos.StudentDefenseDetailsDto;
import com.soutenance.dto.Dtos.StudentDocumentResponse;
import com.soutenance.dto.Dtos.StudentGroupDetailsDto;
import com.soutenance.dto.Dtos.StudentGroupWorkspaceDto;
import com.soutenance.dto.Dtos.StudentStatsDto;
import com.soutenance.service.StudentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/stats")
    public StudentStatsDto getStats() {
        return studentService.getStats();
    }

    @GetMapping("/defense")
    public StudentDefenseDetailsDto getDefense() {
        return studentService.getDefense();
    }

    @GetMapping("/group")
    public StudentGroupWorkspaceDto getGroupWorkspace() {
        return studentService.getGroupWorkspace();
    }

    @PostMapping("/group")
    public StudentGroupDetailsDto createGroup() {
        return studentService.createGroup();
    }

    @PostMapping("/group/{id}/join")
    public StudentGroupDetailsDto joinGroup(@PathVariable String id) {
        return studentService.joinGroup(id);
    }

    @GetMapping("/documents")
    public List<StudentDocumentResponse> getDocuments() {
        return studentService.getDocuments();
    }

    @GetMapping("/convocation")
    public ResponseEntity<byte[]> getConvocation() {
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_PDF)
            .header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment()
                .filename("convocation-soutenance.pdf")
                .build()
                .toString())
            .body(studentService.getConvocationPdf());
    }
}
