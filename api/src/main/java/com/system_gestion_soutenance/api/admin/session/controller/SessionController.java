package com.system_gestion_soutenance.api.admin.session.controller;

import com.system_gestion_soutenance.api.admin.session.dto.CreateSessionRequest;
import com.system_gestion_soutenance.api.admin.session.entity.Session;
import com.system_gestion_soutenance.api.admin.session.service.SessionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/sessions")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping
    public List<Session> findAll() {
        return sessionService.findAll();
    }

    @PostMapping
    public ResponseEntity<Session> create(@Valid @RequestBody CreateSessionRequest request) {
        Session session = sessionService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(session);
    }

    @PutMapping("/{id}")
    public Session update(@PathVariable String id, @Valid @RequestBody CreateSessionRequest request) {
        return sessionService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        sessionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
