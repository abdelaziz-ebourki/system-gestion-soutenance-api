package com.soutenance.service.impl;

import com.soutenance.dto.Dtos.TeacherDefenseResponse;
import com.soutenance.dto.Dtos.TeacherEvaluationResponse;
import com.soutenance.dto.Dtos.TeacherEvaluationSubmitRequest;
import com.soutenance.dto.Dtos.TeacherStatsDto;
import com.soutenance.dto.Dtos.TeacherUnavailabilityDto;
import com.soutenance.entity.DefenseSettings;
import com.soutenance.entity.Jury;
import com.soutenance.entity.Project;
import com.soutenance.entity.Room;
import com.soutenance.entity.Session;
import com.soutenance.entity.Teacher;
import com.soutenance.entity.TeacherEvaluation;
import com.soutenance.entity.TeacherUnavailability;
import com.soutenance.exception.BadRequestException;
import com.soutenance.exception.ResourceNotFoundException;
import com.soutenance.mapper.TeacherMapper;
import com.soutenance.repository.DefenseSettingsRepository;
import com.soutenance.repository.JuryRepository;
import com.soutenance.repository.ProjectRepository;
import com.soutenance.repository.RoomRepository;
import com.soutenance.repository.SessionRepository;
import com.soutenance.repository.TeacherEvaluationRepository;
import com.soutenance.repository.TeacherUnavailabilityRepository;
import com.soutenance.security.CurrentUserService;
import com.soutenance.service.TeacherService;
import com.soutenance.util.DateTimeUtil;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private static final String SETTINGS_ID = "default-settings";

    private final CurrentUserService currentUserService;
    private final JuryRepository juryRepository;
    private final ProjectRepository projectRepository;
    private final RoomRepository roomRepository;
    private final SessionRepository sessionRepository;
    private final DefenseSettingsRepository defenseSettingsRepository;
    private final TeacherEvaluationRepository teacherEvaluationRepository;
    private final TeacherUnavailabilityRepository teacherUnavailabilityRepository;
    private final TeacherMapper teacherMapper;

    @Override
    @Transactional(readOnly = true)
    public TeacherStatsDto getStats() {
        Teacher teacher = currentUserService.teacher();
        List<TeacherDefenseResponse> schedule = getScheduleForTeacher(teacher);
        long upcoming = schedule.stream().filter(item -> "scheduled".equals(item.status())).count();
        long juryAssignments = schedule.stream().filter(item -> !"supervisor".equals(item.role())).count();
        return new TeacherStatsDto(
            upcoming,
            teacherEvaluationRepository.countByTeacherIdAndStatus(teacher.getId(), "pending"),
            teacherUnavailabilityRepository.countByTeacherId(teacher.getId()),
            juryAssignments
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeacherDefenseResponse> getSchedule() {
        return getScheduleForTeacher(currentUserService.teacher());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeacherEvaluationResponse> getEvaluations() {
        Teacher teacher = currentUserService.teacher();
        return teacherEvaluationRepository.findByTeacherId(teacher.getId()).stream()
            .map(teacherMapper::toEvaluationResponse)
            .toList();
    }

    @Override
    @Transactional
    public TeacherEvaluationResponse submitEvaluation(String id, TeacherEvaluationSubmitRequest request) {
        Teacher teacher = currentUserService.teacher();
        TeacherEvaluation evaluation = teacherEvaluationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Evaluation introuvable"));
        if (evaluation.getTeacher() == null || !teacher.getId().equals(evaluation.getTeacher().getId())) {
            throw new BadRequestException("Cette evaluation ne vous est pas affectee");
        }
        evaluation.setScore(request.score());
        evaluation.setComment(request.comment());
        evaluation.setStatus("submitted");
        evaluation.setSubmittedAt(OffsetDateTime.now());
        return teacherMapper.toEvaluationResponse(teacherEvaluationRepository.save(evaluation));
    }

    @Override
    @Transactional(readOnly = true)
    public TeacherUnavailabilityDto getUnavailability() {
        Teacher teacher = currentUserService.teacher();
        return toUnavailabilityDto(teacherUnavailabilityRepository.findByTeacherIdOrderByDateAscStartTimeAsc(teacher.getId()));
    }

    @Override
    @Transactional
    public TeacherUnavailabilityDto saveUnavailability(TeacherUnavailabilityDto request) {
        Teacher teacher = currentUserService.teacher();
        teacherUnavailabilityRepository.deleteByTeacherId(teacher.getId());
        List<TeacherUnavailability> rows = new ArrayList<>();
        if (request.slotsByDate() != null) {
            request.slotsByDate().forEach((date, slots) -> slots.forEach(slot -> rows.add(toEntity(teacher, date, slot))));
        }
        teacherUnavailabilityRepository.saveAll(rows);
        return toUnavailabilityDto(teacherUnavailabilityRepository.findByTeacherIdOrderByDateAscStartTimeAsc(teacher.getId()));
    }

    private List<TeacherDefenseResponse> getScheduleForTeacher(Teacher teacher) {
        List<TeacherDefenseResponse> schedule = new ArrayList<>();
        List<Jury> juries = juryRepository.findByPresidentIdOrReporterIdOrExaminerId(
            teacher.getId(),
            teacher.getId(),
            teacher.getId()
        );
        int index = 0;
        for (Jury jury : juries) {
            String role = roleFor(jury, teacher);
            schedule.add(toDefense(jury.getProject(), role, index++));
        }
        for (Project project : projectRepository.findBySupervisorId(teacher.getId())) {
            schedule.add(toDefense(project, "supervisor", index++));
        }
        return schedule;
    }

    private TeacherDefenseResponse toDefense(Project project, String role, int index) {
        DefenseSettings settings = settings();
        List<Room> rooms = roomRepository.findAll(Sort.by("name"));
        Room room = rooms.isEmpty() ? null : rooms.get(index % rooms.size());
        LocalDate date = baseDate().plusDays(index / Math.max(1, Math.max(1, rooms.size())));
        int slotMinutes = settings.getDefenseDuration() + settings.getBreakDuration();
        LocalTime start = settings.getStartTime().plusMinutes((long) (index % 5) * slotMinutes);
        LocalTime end = start.plusMinutes(settings.getDefenseDuration());
        return teacherMapper.toDefenseResponse(
            "td-" + project.getId() + "-" + role,
            project,
            date,
            start,
            end,
            room == null ? "Salle" : room.getName(),
            role,
            "scheduled"
        );
    }

    private String roleFor(Jury jury, Teacher teacher) {
        if (jury.getPresident().getId().equals(teacher.getId())) {
            return "president";
        }
        if (jury.getReporter().getId().equals(teacher.getId())) {
            return "reporter";
        }
        return "examiner";
    }

    private DefenseSettings settings() {
        return defenseSettingsRepository.findById(SETTINGS_ID)
            .orElseThrow(() -> new BadRequestException("Parametres de soutenance introuvables"));
    }

    private LocalDate baseDate() {
        return sessionRepository.findByStatusOrderByStartDateAsc("active").stream()
            .findFirst()
            .map(Session::getStartDate)
            .orElse(LocalDate.of(2026, 6, 10));
    }

    private TeacherUnavailabilityDto toUnavailabilityDto(List<TeacherUnavailability> rows) {
        Map<String, List<String>> slotsByDate = new LinkedHashMap<>();
        for (TeacherUnavailability row : rows) {
            slotsByDate.computeIfAbsent(DateTimeUtil.date(row.getDate()), ignored -> new ArrayList<>())
                .add(DateTimeUtil.time(row.getStartTime()) + " - " + DateTimeUtil.time(row.getEndTime()));
        }
        return new TeacherUnavailabilityDto(slotsByDate);
    }

    private TeacherUnavailability toEntity(Teacher teacher, String date, String slot) {
        String[] parts = slot.split(" - ");
        if (parts.length != 2) {
            throw new BadRequestException("Creneau invalide: " + slot);
        }
        TeacherUnavailability entity = new TeacherUnavailability();
        entity.setTeacher(teacher);
        entity.setDate(DateTimeUtil.parseDate(date));
        entity.setStartTime(DateTimeUtil.parseTime(parts[0]));
        entity.setEndTime(DateTimeUtil.parseTime(parts[1]));
        return entity;
    }
}
