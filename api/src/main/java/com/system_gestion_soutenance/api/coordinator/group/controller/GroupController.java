package com.system_gestion_soutenance.api.coordinator.group.controller;

import com.system_gestion_soutenance.api.coordinator.group.dto.CreateGroupRequest;
import com.system_gestion_soutenance.api.coordinator.group.service.GroupService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/coordinator/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public List<Map<String, Object>> findAll() {
        return groupService.findAll();
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody CreateGroupRequest request) {
        Map<String, Object> group = groupService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(group);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        groupService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
