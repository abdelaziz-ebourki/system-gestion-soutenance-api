package com.system_gestion_soutenance.api.admin.session.service;

import com.system_gestion_soutenance.api.admin.defensesession.repository.DefenseSessionRepository;
import com.system_gestion_soutenance.api.admin.session.dto.CreateSessionRequest;
import com.system_gestion_soutenance.api.admin.session.entity.Session;
import com.system_gestion_soutenance.api.admin.session.entity.SessionStatus;
import com.system_gestion_soutenance.api.admin.session.repository.SessionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final DefenseSessionRepository defenseSessionRepository;

    public SessionService(SessionRepository sessionRepository, DefenseSessionRepository defenseSessionRepository) {
        this.sessionRepository = sessionRepository;
        this.defenseSessionRepository = defenseSessionRepository;
    }

    public List<Session> findAll() {
        return sessionRepository.findAll();
    }

    public Session create(CreateSessionRequest request) {
        Session session = new Session();
        session.setId(UUID.randomUUID().toString());
        session.setName(request.name());
        session.setType(request.type());
        session.setStatus(parseStatus(request.status()));
        session.setStartDate(LocalDate.parse(request.startDate()));
        session.setEndDate(LocalDate.parse(request.endDate()));
        return sessionRepository.save(session);
    }

    public Session update(String id, CreateSessionRequest request) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Session non trouvée"));

        session.setName(request.name());
        session.setType(request.type());
        session.setStatus(parseStatus(request.status()));
        session.setStartDate(LocalDate.parse(request.startDate()));
        session.setEndDate(LocalDate.parse(request.endDate()));
        return sessionRepository.save(session);
    }

    public void delete(String id) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Session non trouvée"));

        if (!defenseSessionRepository.findByGlobalSession_Id(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Impossible de supprimer cette session car des sessions de soutenance y sont rattachées");
        }

        sessionRepository.delete(session);
    }

    private SessionStatus parseStatus(String status) {
        try {
            return SessionStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Statut invalide. Valeurs autorisées: DRAFT, ACTIVE, ARCHIVED");
        }
    }
}
