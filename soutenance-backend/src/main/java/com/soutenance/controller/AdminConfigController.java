package com.soutenance.controller;

import com.soutenance.dto.Dtos.DefenseSettingsDto;
import com.soutenance.dto.Dtos.SimpleNameRequest;
import com.soutenance.dto.Dtos.SimpleNameResponse;
import com.soutenance.service.ConfigService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/config")
@RequiredArgsConstructor
public class AdminConfigController {

    private final ConfigService configService;

    @GetMapping("/filieres")
    public List<SimpleNameResponse> getFilieres() {
        return configService.getFilieres();
    }

    @PostMapping("/filieres")
    @ResponseStatus(HttpStatus.CREATED)
    public SimpleNameResponse createFiliere(@Valid @RequestBody SimpleNameRequest request) {
        return configService.createFiliere(request);
    }

    @PutMapping("/filieres/{id}")
    public SimpleNameResponse updateFiliere(@PathVariable String id, @Valid @RequestBody SimpleNameRequest request) {
        return configService.updateFiliere(id, request);
    }

    @DeleteMapping("/filieres/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFiliere(@PathVariable String id) {
        configService.deleteFiliere(id);
    }

    @GetMapping("/levels")
    public List<SimpleNameResponse> getLevels() {
        return configService.getLevels();
    }

    @PostMapping("/levels")
    @ResponseStatus(HttpStatus.CREATED)
    public SimpleNameResponse createLevel(@Valid @RequestBody SimpleNameRequest request) {
        return configService.createLevel(request);
    }

    @PutMapping("/levels/{id}")
    public SimpleNameResponse updateLevel(@PathVariable String id, @Valid @RequestBody SimpleNameRequest request) {
        return configService.updateLevel(id, request);
    }

    @DeleteMapping("/levels/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLevel(@PathVariable String id) {
        configService.deleteLevel(id);
    }

    @GetMapping("/grades")
    public List<SimpleNameResponse> getGrades() {
        return configService.getGrades();
    }

    @PostMapping("/grades")
    @ResponseStatus(HttpStatus.CREATED)
    public SimpleNameResponse createGrade(@Valid @RequestBody SimpleNameRequest request) {
        return configService.createGrade(request);
    }

    @PutMapping("/grades/{id}")
    public SimpleNameResponse updateGrade(@PathVariable String id, @Valid @RequestBody SimpleNameRequest request) {
        return configService.updateGrade(id, request);
    }

    @DeleteMapping("/grades/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGrade(@PathVariable String id) {
        configService.deleteGrade(id);
    }

    @GetMapping("/settings")
    public DefenseSettingsDto getSettings() {
        return configService.getSettings();
    }

    @PostMapping("/settings")
    public DefenseSettingsDto updateSettings(@Valid @RequestBody DefenseSettingsDto request) {
        return configService.updateSettings(request);
    }

    @GetMapping("/session-types")
    public List<SimpleNameResponse> getSessionTypes() {
        return configService.getSessionTypes();
    }

    @PostMapping("/session-types")
    @ResponseStatus(HttpStatus.CREATED)
    public SimpleNameResponse createSessionType(@Valid @RequestBody SimpleNameRequest request) {
        return configService.createSessionType(request);
    }

    @DeleteMapping("/session-types/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSessionType(@PathVariable String id) {
        configService.deleteSessionType(id);
    }

    @GetMapping("/session-statuses")
    public List<SimpleNameResponse> getSessionStatuses() {
        return configService.getSessionStatuses();
    }

    @PostMapping("/session-statuses")
    @ResponseStatus(HttpStatus.CREATED)
    public SimpleNameResponse createSessionStatus(@Valid @RequestBody SimpleNameRequest request) {
        return configService.createSessionStatus(request);
    }

    @DeleteMapping("/session-statuses/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSessionStatus(@PathVariable String id) {
        configService.deleteSessionStatus(id);
    }
}
