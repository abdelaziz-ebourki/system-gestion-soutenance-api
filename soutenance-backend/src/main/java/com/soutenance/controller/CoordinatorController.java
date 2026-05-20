package com.soutenance.controller;

import com.soutenance.dto.Dtos.CoordinatorStatsDto;
import com.soutenance.dto.Dtos.GroupRequest;
import com.soutenance.dto.Dtos.GroupResponse;
import com.soutenance.dto.Dtos.JuryRequest;
import com.soutenance.dto.Dtos.JuryResponse;
import com.soutenance.dto.Dtos.MessageResponse;
import com.soutenance.dto.Dtos.ProjectRequest;
import com.soutenance.dto.Dtos.ProjectResponse;
import com.soutenance.dto.Dtos.ScheduleRequest;
import com.soutenance.service.CoordinatorService;
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
@RequestMapping("/api/coordinator")
@RequiredArgsConstructor
public class CoordinatorController {

    private final CoordinatorService coordinatorService;

    @GetMapping("/stats")
    public CoordinatorStatsDto getStats() {
        return coordinatorService.getStats();
    }

    @GetMapping("/projects")
    public List<ProjectResponse> getProjects() {
        return coordinatorService.getProjects();
    }

    @PostMapping("/projects")
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectResponse createProject(@Valid @RequestBody ProjectRequest request) {
        return coordinatorService.createProject(request);
    }

    @PutMapping("/projects/{id}")
    public ProjectResponse updateProject(@PathVariable String id, @Valid @RequestBody ProjectRequest request) {
        return coordinatorService.updateProject(id, request);
    }

    @DeleteMapping("/projects/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@PathVariable String id) {
        coordinatorService.deleteProject(id);
    }

    @GetMapping("/groups")
    public List<GroupResponse> getGroups() {
        return coordinatorService.getGroups();
    }

    @PostMapping("/groups")
    @ResponseStatus(HttpStatus.CREATED)
    public GroupResponse createGroup(@Valid @RequestBody GroupRequest request) {
        return coordinatorService.createGroup(request);
    }

    @DeleteMapping("/groups/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGroup(@PathVariable String id) {
        coordinatorService.deleteGroup(id);
    }

    @GetMapping("/jurys")
    public List<JuryResponse> getJurys() {
        return coordinatorService.getJurys();
    }

    @PostMapping("/jurys")
    @ResponseStatus(HttpStatus.CREATED)
    public JuryResponse createJury(@Valid @RequestBody JuryRequest request) {
        return coordinatorService.createJury(request);
    }

    @PutMapping("/jurys/{id}")
    public JuryResponse updateJury(@PathVariable String id, @Valid @RequestBody JuryRequest request) {
        return coordinatorService.updateJury(id, request);
    }

    @DeleteMapping("/jurys/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteJury(@PathVariable String id) {
        coordinatorService.deleteJury(id);
    }

    @PostMapping("/schedule")
    public MessageResponse saveSchedule(@Valid @RequestBody ScheduleRequest request) {
        return coordinatorService.saveSchedule(request);
    }
}
