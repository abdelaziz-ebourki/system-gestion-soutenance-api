package com.system_gestion_soutenance.api.admin.config.grade.service;

import com.system_gestion_soutenance.api.admin.config.grade.dto.CreateGradeRequest;
import com.system_gestion_soutenance.api.admin.config.grade.entity.Grade;
import com.system_gestion_soutenance.api.admin.config.grade.repository.GradeRepository;
import com.system_gestion_soutenance.api.user.repository.TeacherRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class GradeConfigService {

    private final GradeRepository gradeRepository;
    private final TeacherRepository teacherRepository;

    public GradeConfigService(GradeRepository gradeRepository, TeacherRepository teacherRepository) {
        this.gradeRepository = gradeRepository;
        this.teacherRepository = teacherRepository;
    }

    public List<Grade> findAll() {
        return gradeRepository.findAll();
    }

    public Grade create(CreateGradeRequest request) {
        if (gradeRepository.findByName(request.name()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Un grade avec ce nom existe déjà");
        }

        Grade grade = new Grade();
        grade.setId(UUID.randomUUID().toString());
        grade.setName(request.name());
        return gradeRepository.save(grade);
    }

    public Grade update(String id, CreateGradeRequest request) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Grade non trouvé"));

        grade.setName(request.name());
        return gradeRepository.save(grade);
    }

    public void delete(String id) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Grade non trouvé"));

        if (!teacherRepository.findByGradeId(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Impossible de supprimer ce grade car des enseignants y sont rattachés");
        }

        gradeRepository.delete(grade);
    }
}
