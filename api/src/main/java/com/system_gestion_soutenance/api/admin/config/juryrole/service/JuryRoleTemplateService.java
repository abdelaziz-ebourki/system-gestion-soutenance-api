package com.system_gestion_soutenance.api.admin.config.juryrole.service;

import com.system_gestion_soutenance.api.admin.config.juryrole.dto.CreateJuryRoleTemplateRequest;
import com.system_gestion_soutenance.api.admin.config.juryrole.entity.JuryRoleTemplate;
import com.system_gestion_soutenance.api.admin.config.juryrole.repository.JuryRoleTemplateRepository;
import com.system_gestion_soutenance.api.admin.defensesession.repository.DefenseSessionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class JuryRoleTemplateService {

    private final JuryRoleTemplateRepository juryRoleTemplateRepository;
    private final DefenseSessionRepository defenseSessionRepository;

    public JuryRoleTemplateService(JuryRoleTemplateRepository juryRoleTemplateRepository,
                                    DefenseSessionRepository defenseSessionRepository) {
        this.juryRoleTemplateRepository = juryRoleTemplateRepository;
        this.defenseSessionRepository = defenseSessionRepository;
    }

    public List<JuryRoleTemplate> findAll() {
        return juryRoleTemplateRepository.findAll();
    }

    public JuryRoleTemplate create(CreateJuryRoleTemplateRequest request) {
        if (juryRoleTemplateRepository.findByName(request.name()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Un template avec ce nom existe déjà");
        }

        JuryRoleTemplate template = new JuryRoleTemplate();
        template.setId(UUID.randomUUID().toString());
        template.setName(request.name());
        return juryRoleTemplateRepository.save(template);
    }

    public JuryRoleTemplate update(String id, CreateJuryRoleTemplateRequest request) {
        JuryRoleTemplate template = juryRoleTemplateRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Template de rôle jury non trouvé"));

        template.setName(request.name());
        return juryRoleTemplateRepository.save(template);
    }

    public void delete(String id) {
        JuryRoleTemplate template = juryRoleTemplateRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Template de rôle jury non trouvé"));

        if (!defenseSessionRepository.findByJuryRoleTemplate_Id(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Impossible de supprimer ce template car des sessions de soutenance l'utilisent");
        }

        juryRoleTemplateRepository.delete(template);
    }
}
