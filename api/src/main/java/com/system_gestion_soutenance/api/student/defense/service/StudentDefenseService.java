package com.system_gestion_soutenance.api.student.defense.service;

import com.system_gestion_soutenance.api.coordinator.group.entity.Group;
import com.system_gestion_soutenance.api.coordinator.group.repository.GroupRepository;
import com.system_gestion_soutenance.api.coordinator.jury.entity.Jury;
import com.system_gestion_soutenance.api.coordinator.jury.repository.JuryRepository;
import com.system_gestion_soutenance.api.coordinator.project.entity.Project;
import com.system_gestion_soutenance.api.coordinator.project.repository.ProjectRepository;
import com.system_gestion_soutenance.api.coordinator.schedule.entity.SlotAssignment;
import com.system_gestion_soutenance.api.coordinator.schedule.repository.SlotAssignmentRepository;
import com.system_gestion_soutenance.api.user.entity.Teacher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class StudentDefenseService {

    private final GroupRepository groupRepository;
    private final ProjectRepository projectRepository;
    private final JuryRepository juryRepository;
    private final SlotAssignmentRepository slotAssignmentRepository;

    public StudentDefenseService(GroupRepository groupRepository,
                                  ProjectRepository projectRepository,
                                  JuryRepository juryRepository,
                                  SlotAssignmentRepository slotAssignmentRepository) {
        this.groupRepository = groupRepository;
        this.projectRepository = projectRepository;
        this.juryRepository = juryRepository;
        this.slotAssignmentRepository = slotAssignmentRepository;
    }

    public Map<String, Object> getDefense(String studentId) {
        Group group = findGroupForStudent(studentId);
        if (group == null || group.getProject() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Aucune soutenance trouvée pour cet étudiant");
        }

        Project project = group.getProject();
        Map<String, Object> defense = new LinkedHashMap<>();
        defense.put("projectTitle", project.getTitle());
        defense.put("projectDescription", project.getDescription());

        defense.put("supervisorName", project.getSupervisor() != null
                ? project.getSupervisor().getFirstName() + " " + project.getSupervisor().getLastName()
                : null);

        List<Map<String, String>> juryMembers = new ArrayList<>();
        for (Jury jury : juryRepository.findByProjectId(project.getId())) {
            addJuryMember(juryMembers, "Président", jury.getPresident());
            addJuryMember(juryMembers, "Rapporteur", jury.getReporter());
            addJuryMember(juryMembers, "Examinateur", jury.getExaminer());
        }
        defense.put("juryMembers", juryMembers);

        List<SlotAssignment> slots = new ArrayList<>();
        for (SlotAssignment s : slotAssignmentRepository.findAll()) {
            if (project.getId().equals(s.getProjectId())) {
                slots.add(s);
            }
        }
        if (!slots.isEmpty()) {
            SlotAssignment slot = slots.get(0);
            defense.put("date", slot.getDate());
            defense.put("startTime", slot.getTime());
            defense.put("endTime", "");
            defense.put("roomName", slot.getRoom() != null ? slot.getRoom().getName() : "");
            defense.put("status", "scheduled");
        } else {
            defense.put("status", "pending");
        }

        defense.put("convocationUrl", null);
        defense.put("result", null);

        return defense;
    }

    private Group findGroupForStudent(String studentId) {
        for (Group g : groupRepository.findAll()) {
            if (g.getStudents() != null &&
                    g.getStudents().stream().anyMatch(s -> s.getId().equals(studentId))) {
                return g;
            }
        }
        return null;
    }

    private void addJuryMember(List<Map<String, String>> members, String role, Teacher teacher) {
        if (teacher != null) {
            Map<String, String> m = new LinkedHashMap<>();
            m.put("name", teacher.getFirstName() + " " + teacher.getLastName());
            m.put("role", role);
            members.add(m);
        }
    }
}
