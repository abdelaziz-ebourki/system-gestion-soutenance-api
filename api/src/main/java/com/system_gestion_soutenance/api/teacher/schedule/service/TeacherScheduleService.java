package com.system_gestion_soutenance.api.teacher.schedule.service;

import com.system_gestion_soutenance.api.admin.room.entity.Room;
import com.system_gestion_soutenance.api.coordinator.group.entity.Group;
import com.system_gestion_soutenance.api.coordinator.group.repository.GroupRepository;
import com.system_gestion_soutenance.api.coordinator.jury.entity.Jury;
import com.system_gestion_soutenance.api.coordinator.jury.repository.JuryRepository;
import com.system_gestion_soutenance.api.coordinator.project.entity.Project;
import com.system_gestion_soutenance.api.coordinator.project.repository.ProjectRepository;
import com.system_gestion_soutenance.api.coordinator.schedule.entity.SlotAssignment;
import com.system_gestion_soutenance.api.coordinator.schedule.repository.SlotAssignmentRepository;
import com.system_gestion_soutenance.api.user.entity.Student;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TeacherScheduleService {

    private final SlotAssignmentRepository slotAssignmentRepository;
    private final JuryRepository juryRepository;
    private final ProjectRepository projectRepository;
    private final GroupRepository groupRepository;

    public TeacherScheduleService(SlotAssignmentRepository slotAssignmentRepository,
                                   JuryRepository juryRepository,
                                   ProjectRepository projectRepository,
                                   GroupRepository groupRepository) {
        this.slotAssignmentRepository = slotAssignmentRepository;
        this.juryRepository = juryRepository;
        this.projectRepository = projectRepository;
        this.groupRepository = groupRepository;
    }

    public List<Map<String, Object>> getSchedule(String teacherId) {
        Set<String> projectIdsForTeacher = new HashSet<>();

        for (Jury jury : juryRepository.findAll()) {
            if (matchesTeacher(jury, teacherId)) {
                projectIdsForTeacher.add(jury.getProject().getId());
            }
        }

        for (Project project : projectRepository.findAll()) {
            if (project.getSupervisor() != null && project.getSupervisor().getId().equals(teacherId)) {
                projectIdsForTeacher.add(project.getId());
            }
        }

        Map<String, String> projectRoles = new HashMap<>();
        for (Jury jury : juryRepository.findAll()) {
            String pid = jury.getProject().getId();
            if (projectIdsForTeacher.contains(pid)) {
                if (jury.getPresident().getId().equals(teacherId))
                    projectRoles.put(pid, "president");
                else if (jury.getReporter().getId().equals(teacherId))
                    projectRoles.put(pid, "reporter");
                else if (jury.getExaminer().getId().equals(teacherId))
                    projectRoles.put(pid, "examiner");
            }
        }
        for (Project project : projectRepository.findAll()) {
            String pid = project.getId();
            if (projectIdsForTeacher.contains(pid) && !projectRoles.containsKey(pid)) {
                if (project.getSupervisor() != null && project.getSupervisor().getId().equals(teacherId)) {
                    projectRoles.put(pid, "supervisor");
                }
            }
        }

        Map<String, List<String>> projectStudents = new HashMap<>();
        for (Group group : groupRepository.findAll()) {
            String pid = group.getProject().getId();
            if (projectIdsForTeacher.contains(pid)) {
                List<String> names = group.getStudents() != null
                        ? group.getStudents().stream()
                            .map(s -> s.getFirstName() + " " + s.getLastName())
                            .collect(Collectors.toList())
                        : List.of();
                projectStudents.put(pid, names);
            }
        }
        for (Project project : projectRepository.findAll()) {
            String pid = project.getId();
            if (projectIdsForTeacher.contains(pid) && !projectStudents.containsKey(pid)) {
                List<String> names = project.getStudents() != null
                        ? project.getStudents().stream()
                            .map(s -> s.getFirstName() + " " + s.getLastName())
                            .collect(Collectors.toList())
                        : List.of();
                projectStudents.put(pid, names);
            }
        }

        List<Map<String, Object>> result = new ArrayList<>();
        for (SlotAssignment slot : slotAssignmentRepository.findAll()) {
            String pid = slot.getProjectId();
            if (pid == null || !projectIdsForTeacher.contains(pid)) continue;

            Project project = projectRepository.findById(pid).orElse(null);
            if (project == null) continue;

            Map<String, Object> entry = new LinkedHashMap<>();
            entry.put("id", slot.getId());
            entry.put("projectId", pid);
            entry.put("projectTitle", project.getTitle());
            entry.put("studentNames", projectStudents.getOrDefault(pid, List.of()));
            entry.put("date", slot.getDate());
            entry.put("startTime", slot.getTime());
            entry.put("endTime", "");
            entry.put("roomName", slot.getRoom() != null ? slot.getRoom().getName() : "");
            entry.put("role", projectRoles.getOrDefault(pid, ""));
            entry.put("status", "scheduled");
            result.add(entry);
        }

        return result;
    }

    private boolean matchesTeacher(Jury jury, String teacherId) {
        return jury.getPresident().getId().equals(teacherId)
                || jury.getReporter().getId().equals(teacherId)
                || jury.getExaminer().getId().equals(teacherId);
    }
}
