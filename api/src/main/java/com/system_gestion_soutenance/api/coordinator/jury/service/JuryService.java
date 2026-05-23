package com.system_gestion_soutenance.api.coordinator.jury.service;

import com.system_gestion_soutenance.api.coordinator.jury.dto.CreateJuryRequest;
import com.system_gestion_soutenance.api.coordinator.jury.entity.Jury;
import com.system_gestion_soutenance.api.coordinator.jury.repository.JuryRepository;
import com.system_gestion_soutenance.api.coordinator.project.entity.Project;
import com.system_gestion_soutenance.api.coordinator.project.repository.ProjectRepository;
import com.system_gestion_soutenance.api.user.entity.Teacher;
import com.system_gestion_soutenance.api.user.repository.TeacherRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JuryService {

    private final JuryRepository juryRepository;
    private final ProjectRepository projectRepository;
    private final TeacherRepository teacherRepository;

    public JuryService(JuryRepository juryRepository,
                        ProjectRepository projectRepository,
                        TeacherRepository teacherRepository) {
        this.juryRepository = juryRepository;
        this.projectRepository = projectRepository;
        this.teacherRepository = teacherRepository;
    }

    public List<Map<String, Object>> findAll() {
        return juryRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Map<String, Object> create(CreateJuryRequest request) {
        Project project = projectRepository.findById(request.projectId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Projet introuvable"));

        Teacher president = teacherRepository.findById(request.presidentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Président introuvable"));
        Teacher reporter = teacherRepository.findById(request.reporterId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Rapporteur introuvable"));
        Teacher examiner = teacherRepository.findById(request.examinerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Examinateur introuvable"));

        Jury jury = new Jury();
        jury.setId(UUID.randomUUID().toString());
        jury.setProject(project);
        jury.setPresident(president);
        jury.setReporter(reporter);
        jury.setExaminer(examiner);

        return toResponse(juryRepository.save(jury));
    }

    public Map<String, Object> update(String id, Map<String, Object> updates) {
        Jury jury = juryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Jury non trouvé"));

        if (updates.containsKey("projectId")) {
            Project project = projectRepository.findById((String) updates.get("projectId"))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Projet introuvable"));
            jury.setProject(project);
        }
        if (updates.containsKey("presidentId")) {
            Teacher teacher = teacherRepository.findById((String) updates.get("presidentId"))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Président introuvable"));
            jury.setPresident(teacher);
        }
        if (updates.containsKey("reporterId")) {
            Teacher teacher = teacherRepository.findById((String) updates.get("reporterId"))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Rapporteur introuvable"));
            jury.setReporter(teacher);
        }
        if (updates.containsKey("examinerId")) {
            Teacher teacher = teacherRepository.findById((String) updates.get("examinerId"))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Examinateur introuvable"));
            jury.setExaminer(teacher);
        }

        return toResponse(juryRepository.save(jury));
    }

    public void delete(String id) {
        if (!juryRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Jury non trouvé");
        }
        juryRepository.deleteById(id);
    }

    private Map<String, Object> toResponse(Jury jury) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", jury.getId());
        map.put("projectId", jury.getProject().getId());
        map.put("projectTitle", jury.getProject().getTitle());
        map.put("defenseType", jury.getProject().getDefenseType());

        map.put("presidentId", jury.getPresident().getId());
        map.put("presidentName", jury.getPresident().getFirstName() + " " + jury.getPresident().getLastName());
        map.put("reporterId", jury.getReporter().getId());
        map.put("reporterName", jury.getReporter().getFirstName() + " " + jury.getReporter().getLastName());
        map.put("examinerId", jury.getExaminer().getId());
        map.put("examinerName", jury.getExaminer().getFirstName() + " " + jury.getExaminer().getLastName());

        return map;
    }
}
