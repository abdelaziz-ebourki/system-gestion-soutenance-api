package com.system_gestion_soutenance.api.coordinator.document.controller;

import com.system_gestion_soutenance.api.coordinator.document.dto.DefenseIdsRequest;
import com.system_gestion_soutenance.api.coordinator.document.dto.SessionRequest;
import com.system_gestion_soutenance.api.coordinator.document.service.DocumentDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/coordinator/documents")
@Tag(name = "Coordinator - Documents", description = "Données pour les documents PDF (fiches d'évaluation, listes de présence, convocations, planning)")
public class DocumentDataController {

    private final DocumentDataService documentDataService;

    public DocumentDataController(DocumentDataService documentDataService) {
        this.documentDataService = documentDataService;
    }

    @PostMapping("/evaluation-sheets")
    @Operation(summary = "Get evaluation sheets data")
    public ResponseEntity<List<Map<String, Object>>> evaluationSheets(
            @Valid @RequestBody DefenseIdsRequest request) {
        return ResponseEntity.ok(documentDataService.evaluationSheets(request.defenseIds()));
    }

    @PostMapping("/attendance-lists")
    @Operation(summary = "Get attendance lists data")
    public ResponseEntity<Map<String, Object>> attendanceList(
            @Valid @RequestBody SessionRequest request) {
        return ResponseEntity.ok(documentDataService.attendanceList(request.defenseSessionId()));
    }

    @PostMapping("/jury-convocations")
    @Operation(summary = "Get jury convocation data")
    public ResponseEntity<List<Map<String, Object>>> juryConvocations(
            @Valid @RequestBody DefenseIdsRequest request) {
        return ResponseEntity.ok(documentDataService.juryConvocations(request.defenseIds()));
    }

    @PostMapping("/schedule")
    @Operation(summary = "Get printable schedule data")
    public ResponseEntity<Map<String, Object>> schedule(
            @Valid @RequestBody SessionRequest request) {
        return ResponseEntity.ok(documentDataService.schedule(request.defenseSessionId()));
    }
}
