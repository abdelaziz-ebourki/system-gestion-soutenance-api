package com.soutenance.controller;

import com.soutenance.dto.Dtos.TeacherDefenseResponse;
import com.soutenance.dto.Dtos.TeacherEvaluationResponse;
import com.soutenance.dto.Dtos.TeacherEvaluationSubmitRequest;
import com.soutenance.dto.Dtos.TeacherStatsDto;
import com.soutenance.dto.Dtos.TeacherUnavailabilityDto;
import com.soutenance.service.TeacherService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping("/stats")
    public TeacherStatsDto getStats() {
        return teacherService.getStats();
    }

    @GetMapping("/schedule")
    public List<TeacherDefenseResponse> getSchedule() {
        return teacherService.getSchedule();
    }

    @GetMapping("/evaluations")
    public List<TeacherEvaluationResponse> getEvaluations() {
        return teacherService.getEvaluations();
    }

    @PostMapping("/evaluations/{id}")
    public TeacherEvaluationResponse submitEvaluation(
        @PathVariable String id,
        @Valid @RequestBody TeacherEvaluationSubmitRequest request
    ) {
        return teacherService.submitEvaluation(id, request);
    }

    @GetMapping("/unavailability")
    public TeacherUnavailabilityDto getUnavailability() {
        return teacherService.getUnavailability();
    }

    @PostMapping("/unavailability")
    public TeacherUnavailabilityDto saveUnavailability(@RequestBody TeacherUnavailabilityDto request) {
        return teacherService.saveUnavailability(request);
    }
}
