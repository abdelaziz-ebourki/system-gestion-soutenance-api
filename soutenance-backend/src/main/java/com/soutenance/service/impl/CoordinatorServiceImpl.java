package com.soutenance.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soutenance.dto.Dtos.CoordinatorStatsDto;
import com.soutenance.dto.Dtos.GroupRequest;
import com.soutenance.dto.Dtos.GroupResponse;
import com.soutenance.dto.Dtos.JuryRequest;
import com.soutenance.dto.Dtos.JuryResponse;
import com.soutenance.dto.Dtos.MessageResponse;
import com.soutenance.dto.Dtos.ProjectRequest;
import com.soutenance.dto.Dtos.ProjectResponse;
import com.soutenance.dto.Dtos.ScheduleCard;
import com.soutenance.dto.Dtos.ScheduleRequest;
import com.soutenance.entity.DefenseSettings;
import com.soutenance.entity.Jury;
import com.soutenance.entity.Project;
import com.soutenance.entity.Session;
import com.soutenance.entity.Student;
import com.soutenance.entity.StudentGroup;
import com.soutenance.entity.Teacher;
import com.soutenance.entity.TeacherEvaluation;
import com.soutenance.exception.BadRequestException;
import com.soutenance.exception.ResourceNotFoundException;
import com.soutenance.mapper.JuryMapper;
import com.soutenance.mapper.ProjectMapper;
import com.soutenance.mapper.StudentGroupMapper;
import com.soutenance.repository.DefenseSettingsRepository;
import com.soutenance.repository.JuryRepository;
import com.soutenance.repository.ProjectRepository;
import com.soutenance.repository.SessionRepository;
import com.soutenance.repository.StudentGroupRepository;
import com.soutenance.repository.StudentRepository;
import com.soutenance.repository.TeacherEvaluationRepository;
import com.soutenance.repository.TeacherRepository;
import com.soutenance.service.CoordinatorService;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CoordinatorServiceImpl implements CoordinatorService {

    private static final String SETTINGS_ID = "default-settings";

    private final ProjectRepository projectRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final JuryRepository juryRepository;
    private final StudentGroupRepository studentGroupRepository;
    private final SessionRepository sessionRepository;
    private final TeacherEvaluationRepository teacherEvaluationRepository;
    private final DefenseSettingsRepository defenseSettingsRepository;
    private final ObjectMapper objectMapper;
    private final ProjectMapper projectMapper;
    private final JuryMapper juryMapper;
    private final StudentGroupMapper studentGroupMapper;

    @Override
    @Transactional(readOnly = true)
    public CoordinatorStatsDto getStats() {
        return new CoordinatorStatsDto(
            projectRepository.count(),
            studentGroupRepository.count(),
            juryRepository.count(),
            scheduledDefenseCount()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponse> getProjects() {
        return projectRepository.findAll(Sort.by("title")).stream().map(projectMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public ProjectResponse createProject(ProjectRequest request) {
        Project project = new Project();
        applyProject(project, request);
        Project saved = projectRepository.save(project);
        saved.getStudents().forEach(student -> student.setProject(saved));
        return projectMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public ProjectResponse updateProject(String id, ProjectRequest request) {
        Project project = findProject(id);
        project.getStudents().forEach(student -> {
            if (!request.studentIds().contains(student.getId())) {
                student.setProject(null);
            }
        });
        applyProject(project, request);
        Project saved = projectRepository.save(project);
        saved.getStudents().forEach(student -> student.setProject(saved));
        return projectMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void deleteProject(String id) {
        Project project = findProject(id);
        juryRepository.findByProjectId(id).ifPresent(juryRepository::delete);
        teacherEvaluationRepository.deleteByProjectId(id);
        project.getStudents().forEach(student -> student.setProject(null));
        projectRepository.delete(project);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupResponse> getGroups() {
        return studentGroupRepository.findAll(Sort.by("groupName")).stream()
            .map(studentGroupMapper::toCoordinatorResponse)
            .toList();
    }

    @Override
    @Transactional
    public GroupResponse createGroup(GroupRequest request) {
        StudentGroup group = new StudentGroup();
        group.setGroupName(nextGroupName());
        group.setProject(findProject(request.projectId()));
        group.setSession(findSession(request.sessionId()));
        group.setMembers(new LinkedHashSet<>(findStudents(request.studentIds())));
        StudentGroup saved = studentGroupRepository.save(group);
        return studentGroupMapper.toCoordinatorResponse(saved);
    }

    @Override
    @Transactional
    public void deleteGroup(String id) {
        StudentGroup group = studentGroupRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Groupe introuvable"));
        studentGroupRepository.delete(group);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JuryResponse> getJurys() {
        return juryRepository.findAll(Sort.by("project.title")).stream().map(juryMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public JuryResponse createJury(JuryRequest request) {
        if (juryRepository.existsByProjectId(request.projectId())) {
            throw new BadRequestException("Ce projet dispose deja d'un jury");
        }
        Jury jury = new Jury();
        applyJury(jury, request);
        Jury saved = juryRepository.save(jury);
        recreateEvaluations(saved);
        return juryMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public JuryResponse updateJury(String id, JuryRequest request) {
        Jury jury = juryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Jury introuvable"));
        teacherEvaluationRepository.deleteByProjectId(jury.getProject().getId());
        applyJury(jury, request);
        Jury saved = juryRepository.save(jury);
        recreateEvaluations(saved);
        return juryMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void deleteJury(String id) {
        Jury jury = juryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Jury introuvable"));
        teacherEvaluationRepository.deleteByProjectId(jury.getProject().getId());
        juryRepository.delete(jury);
    }

    @Override
    @Transactional
    public MessageResponse saveSchedule(ScheduleRequest request) {
        try {
            DefenseSettings settings = defenseSettingsRepository.findById(SETTINGS_ID)
                .orElseGet(DefenseSettings::new);
            settings.setId(SETTINGS_ID);
            if (settings.getStartTime() == null) {
                settings.setStartTime(java.time.LocalTime.of(8, 0));
                settings.setEndTime(java.time.LocalTime.of(18, 0));
                settings.setDefenseDuration(30);
                settings.setBreakDuration(15);
                settings.setGroupCreationStartDate(java.time.LocalDate.of(2026, 5, 1));
                settings.setGroupCreationEndDate(java.time.LocalDate.of(2026, 6, 20));
            }
            settings.setScheduleJson(objectMapper.writeValueAsString(request.schedule()));
            defenseSettingsRepository.save(settings);
            return new MessageResponse("Schedule saved successfully");
        } catch (Exception ex) {
            throw new BadRequestException("Planning invalide");
        }
    }

    private void applyProject(Project project, ProjectRequest request) {
        project.setTitle(request.title());
        project.setDescription(request.description());
        project.setSupervisor(findTeacher(request.supervisorId()));
        project.setStatus(request.status() == null || request.status().isBlank() ? "pending" : request.status());
        Set<Student> students = new LinkedHashSet<>(findStudents(request.studentIds()));
        project.setStudents(students);
    }

    private void applyJury(Jury jury, JuryRequest request) {
        if (Set.of(request.presidentId(), request.reporterId(), request.examinerId()).size() != 3) {
            throw new BadRequestException("Les membres du jury doivent etre differents");
        }
        jury.setProject(findProject(request.projectId()));
        jury.setPresident(findTeacher(request.presidentId()));
        jury.setReporter(findTeacher(request.reporterId()));
        jury.setExaminer(findTeacher(request.examinerId()));
    }

    private void recreateEvaluations(Jury jury) {
        teacherEvaluationRepository.deleteByProjectId(jury.getProject().getId());
        createEvaluation(jury.getProject(), jury.getPresident(), "president");
        createEvaluation(jury.getProject(), jury.getReporter(), "reporter");
        createEvaluation(jury.getProject(), jury.getExaminer(), "examiner");
        if (jury.getProject().getSupervisor() != null) {
            createEvaluation(jury.getProject(), jury.getProject().getSupervisor(), "supervisor");
        }
    }

    private void createEvaluation(Project project, Teacher teacher, String role) {
        TeacherEvaluation evaluation = new TeacherEvaluation();
        evaluation.setProject(project);
        evaluation.setTeacher(teacher);
        evaluation.setDefenseId("td-" + project.getId() + "-" + role);
        evaluation.setProjectTitle(project.getTitle());
        evaluation.setRole(role);
        evaluation.setStatus("pending");
        teacherEvaluationRepository.save(evaluation);
    }

    private Project findProject(String id) {
        return projectRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Projet introuvable"));
    }

    private Teacher findTeacher(String id) {
        return teacherRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Enseignant introuvable"));
    }

    private List<Student> findStudents(List<String> ids) {
        List<Student> students = studentRepository.findAllById(ids);
        if (students.size() != ids.size()) {
            throw new ResourceNotFoundException("Un ou plusieurs etudiants sont introuvables");
        }
        return students;
    }

    private Session findSession(String id) {
        return sessionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Session introuvable"));
    }

    private String nextGroupName() {
        return "Groupe-" + (studentGroupRepository.count() + 1);
    }

    private long scheduledDefenseCount() {
        return defenseSettingsRepository.findById(SETTINGS_ID)
            .map(DefenseSettings::getScheduleJson)
            .filter(json -> json != null && !json.isBlank())
            .map(json -> {
                try {
                    Map<String, ScheduleCard> schedule = objectMapper.readValue(json, new TypeReference<>() {});
                    return (long) schedule.size();
                } catch (Exception ex) {
                    return 0L;
                }
            })
            .orElse(0L);
    }
}
