package com.system_gestion_soutenance.api.admin.config.level.service;

import com.system_gestion_soutenance.api.admin.config.level.dto.CreateLevelRequest;
import com.system_gestion_soutenance.api.admin.config.level.entity.Level;
import com.system_gestion_soutenance.api.admin.config.level.repository.LevelRepository;
import com.system_gestion_soutenance.api.user.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class LevelConfigService {

    private final LevelRepository levelRepository;
    private final StudentRepository studentRepository;

    public LevelConfigService(LevelRepository levelRepository, StudentRepository studentRepository) {
        this.levelRepository = levelRepository;
        this.studentRepository = studentRepository;
    }

    public List<Level> findAll() {
        return levelRepository.findAll();
    }

    public Level create(CreateLevelRequest request) {
        if (levelRepository.findByName(request.name()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Un niveau avec ce nom existe déjà");
        }

        Level level = new Level();
        level.setId(UUID.randomUUID().toString());
        level.setName(request.name());
        return levelRepository.save(level);
    }

    public Level update(String id, CreateLevelRequest request) {
        Level level = levelRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Niveau non trouvé"));

        level.setName(request.name());
        return levelRepository.save(level);
    }

    public void delete(String id) {
        Level level = levelRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Niveau non trouvé"));

        if (!studentRepository.findByLevelId(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Impossible de supprimer ce niveau car des étudiants y sont inscrits");
        }

        levelRepository.delete(level);
    }
}
