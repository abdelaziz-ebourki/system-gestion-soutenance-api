package com.soutenance.service.impl;

import com.soutenance.dto.Dtos.AvailableGroupDto;
import com.soutenance.dto.Dtos.JuryMemberDto;
import com.soutenance.dto.Dtos.StudentDefenseDetailsDto;
import com.soutenance.dto.Dtos.StudentDocumentResponse;
import com.soutenance.dto.Dtos.StudentGroupDetailsDto;
import com.soutenance.dto.Dtos.StudentGroupWorkspaceDto;
import com.soutenance.dto.Dtos.StudentStatsDto;
import com.soutenance.entity.DefenseSettings;
import com.soutenance.entity.Jury;
import com.soutenance.entity.Project;
import com.soutenance.entity.Room;
import com.soutenance.entity.Student;
import com.soutenance.entity.StudentGroup;
import com.soutenance.exception.BadRequestException;
import com.soutenance.exception.ResourceNotFoundException;
import com.soutenance.mapper.StudentGroupMapper;
import com.soutenance.repository.DefenseSettingsRepository;
import com.soutenance.repository.JuryRepository;
import com.soutenance.repository.RoomRepository;
import com.soutenance.repository.StudentDocumentRepository;
import com.soutenance.repository.StudentGroupRepository;
import com.soutenance.security.CurrentUserService;
import com.soutenance.service.StudentService;
import com.soutenance.util.DateTimeUtil;
import com.soutenance.util.SimplePdfGenerator;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private static final String SETTINGS_ID = "default-settings";

    private final CurrentUserService currentUserService;
    private final StudentGroupRepository studentGroupRepository;
    private final StudentDocumentRepository studentDocumentRepository;
    private final JuryRepository juryRepository;
    private final RoomRepository roomRepository;
    private final DefenseSettingsRepository defenseSettingsRepository;
    private final StudentGroupMapper studentGroupMapper;

    @Override
    @Transactional(readOnly = true)
    public StudentStatsDto getStats() {
        Student student = currentUserService.student();
        StudentGroup group = currentGroup(student);
        StudentDefenseDetailsDto defense = getDefense();
        return new StudentStatsDto(
            studentDocumentRepository.count(),
            studentDocumentRepository.countByStatus("missing"),
            group == null ? 0 : group.getMembers().size(),
            defense.status()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public StudentDefenseDetailsDto getDefense() {
        Student student = currentUserService.student();
        StudentGroup group = currentGroup(student);
        Project project = group == null ? student.getProject() : group.getProject();
        if (project == null) {
            return new StudentDefenseDetailsDto(
                "Aucun projet affecte",
                "Creez ou rejoignez un groupe pendant la periode autorisee pour demarrer votre dossier.",
                "En attente",
                List.of(),
                null,
                null,
                null,
                null,
                "pending",
                null,
                null
            );
        }

        Jury jury = juryRepository.findByProjectId(project.getId()).orElse(null);
        boolean scheduled = jury != null;
        DefenseSlot slot = scheduled ? slotFor(project) : null;
        return new StudentDefenseDetailsDto(
            project.getTitle(),
            project.getDescription(),
            project.getSupervisor() == null ? "En attente" : project.getSupervisor().fullName(),
            jury == null ? List.of() : List.of(
                new JuryMemberDto(jury.getPresident().fullName(), "President"),
                new JuryMemberDto(jury.getReporter().fullName(), "Rapporteur"),
                new JuryMemberDto(jury.getExaminer().fullName(), "Examinateur")
            ),
            slot == null ? null : DateTimeUtil.date(slot.date()),
            slot == null ? null : DateTimeUtil.time(slot.startTime()),
            slot == null ? null : DateTimeUtil.time(slot.endTime()),
            slot == null ? null : slot.roomName(),
            scheduled ? "scheduled" : "pending",
            scheduled ? "/api/student/convocation" : null,
            null
        );
    }

    @Override
    @Transactional(readOnly = true)
    public StudentGroupWorkspaceDto getGroupWorkspace() {
        Student student = currentUserService.student();
        StudentGroup current = currentGroup(student);
        DefenseSettings settings = settings();
        List<AvailableGroupDto> availableGroups = studentGroupRepository.findAvailableForStudent(student.getId()).stream()
            .sorted(Comparator.comparing(StudentGroup::getGroupName))
            .map(studentGroupMapper::toAvailableGroup)
            .toList();
        return new StudentGroupWorkspaceDto(
            current == null ? null : studentGroupMapper.toStudentDetails(current),
            availableGroups,
            DateTimeUtil.date(settings.getGroupCreationStartDate()),
            DateTimeUtil.date(settings.getGroupCreationEndDate()),
            isGroupCreationOpen(settings)
        );
    }

    @Override
    @Transactional
    public StudentGroupDetailsDto createGroup() {
        Student student = currentUserService.student();
        DefenseSettings settings = settings();
        if (!isGroupCreationOpen(settings)) {
            throw new BadRequestException("La creation de groupe n'est pas autorisee en dehors de la periode configuree.");
        }
        if (currentGroup(student) != null) {
            throw new BadRequestException("Vous appartenez deja a un groupe pour cette session.");
        }
        StudentGroup group = new StudentGroup();
        group.setGroupName("Groupe-" + (studentGroupRepository.count() + 1));
        group.getMembers().add(student);
        return studentGroupMapper.toStudentDetails(studentGroupRepository.save(group));
    }

    @Override
    @Transactional
    public StudentGroupDetailsDto joinGroup(String groupId) {
        Student student = currentUserService.student();
        DefenseSettings settings = settings();
        if (!isGroupCreationOpen(settings)) {
            throw new BadRequestException("Vous ne pouvez pas rejoindre un groupe en dehors de la periode configuree.");
        }
        if (currentGroup(student) != null) {
            throw new BadRequestException("Vous appartenez deja a un groupe pour cette session.");
        }
        StudentGroup group = studentGroupRepository.findById(groupId)
            .orElseThrow(() -> new ResourceNotFoundException("Groupe introuvable"));
        group.getMembers().add(student);
        return studentGroupMapper.toStudentDetails(studentGroupRepository.save(group));
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentDocumentResponse> getDocuments() {
        return studentDocumentRepository.findAll(Sort.by("deadline")).stream()
            .map(document -> new StudentDocumentResponse(
                document.getId(),
                document.getName(),
                document.getType(),
                DateTimeUtil.date(document.getDeadline()),
                document.getStatus(),
                DateTimeUtil.offset(document.getSubmittedAt())
            ))
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] getConvocationPdf() {
        StudentDefenseDetailsDto defense = getDefense();
        if (!"scheduled".equals(defense.status())) {
            throw new ResourceNotFoundException("Aucune convocation n'est disponible pour le moment.");
        }
        return SimplePdfGenerator.singlePage("CONVOCATION DE SOUTENANCE", List.of(
            "Projet: " + defense.projectTitle(),
            "Date: " + defense.date(),
            "Horaire: " + defense.startTime() + " - " + defense.endTime(),
            "Salle: " + defense.roomName(),
            "Encadrant: " + defense.supervisorName()
        ));
    }

    private StudentGroup currentGroup(Student student) {
        return studentGroupRepository.findByMemberId(student.getId()).orElse(null);
    }

    private DefenseSettings settings() {
        return defenseSettingsRepository.findById(SETTINGS_ID)
            .orElseThrow(() -> new BadRequestException("Parametres de soutenance introuvables"));
    }

    private boolean isGroupCreationOpen(DefenseSettings settings) {
        LocalDate today = LocalDate.now();
        return !today.isBefore(settings.getGroupCreationStartDate()) && !today.isAfter(settings.getGroupCreationEndDate());
    }

    private DefenseSlot slotFor(Project project) {
        DefenseSettings settings = settings();
        List<Room> rooms = roomRepository.findAll(Sort.by("name"));
        Room room = rooms.isEmpty() ? null : rooms.get(Math.abs(project.getId().hashCode()) % rooms.size());
        LocalDate date = settings.getGroupCreationEndDate().plusDays(1);
        LocalTime start = settings.getStartTime().plusHours(2);
        return new DefenseSlot(
            date,
            start,
            start.plusMinutes(settings.getDefenseDuration()),
            room == null ? "Salle" : room.getName()
        );
    }

    private record DefenseSlot(LocalDate date, LocalTime startTime, LocalTime endTime, String roomName) {
    }
}
