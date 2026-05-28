package com.system_gestion_soutenance.api.coordinator.defensesession.service;

import com.system_gestion_soutenance.api.admin.defensesession.entity.DefenseSession;
import com.system_gestion_soutenance.api.admin.defensesession.service.DefenseSessionService;
import com.system_gestion_soutenance.api.admin.defensesession.repository.DefenseSessionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CoordinatorDefenseSessionService {

    private final DefenseSessionService adminDefenseSessionService;
    private final DefenseSessionRepository defenseSessionRepository;

    public CoordinatorDefenseSessionService(DefenseSessionService adminDefenseSessionService,
                                              DefenseSessionRepository defenseSessionRepository) {
        this.adminDefenseSessionService = adminDefenseSessionService;
        this.defenseSessionRepository = defenseSessionRepository;
    }

    public List<DefenseSession> findAll() {
        return defenseSessionRepository.findAll();
    }

    public DefenseSession transition(Long id, String toStatus) {
        return adminDefenseSessionService.transition(id, toStatus);
    }
}
