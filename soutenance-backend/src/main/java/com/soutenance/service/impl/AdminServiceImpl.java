package com.soutenance.service.impl;

import com.soutenance.dto.Dtos.AuditLogResponse;
import com.soutenance.dto.Dtos.BulkRoomsRequest;
import com.soutenance.dto.Dtos.BulkUsersRequest;
import com.soutenance.dto.Dtos.DashboardStatsDto;
import com.soutenance.dto.Dtos.DepartmentRequest;
import com.soutenance.dto.Dtos.DepartmentResponse;
import com.soutenance.dto.Dtos.PaginatedResponse;
import com.soutenance.dto.Dtos.RoomRequest;
import com.soutenance.dto.Dtos.RoomResponse;
import com.soutenance.dto.Dtos.SessionRequest;
import com.soutenance.dto.Dtos.SessionResponse;
import com.soutenance.dto.Dtos.UserRequest;
import com.soutenance.dto.Dtos.UserResponse;
import com.soutenance.entity.AuditLog;
import com.soutenance.entity.Department;
import com.soutenance.entity.Filiere;
import com.soutenance.entity.Grade;
import com.soutenance.entity.Level;
import com.soutenance.entity.Role;
import com.soutenance.entity.Room;
import com.soutenance.entity.Session;
import com.soutenance.entity.Student;
import com.soutenance.entity.Teacher;
import com.soutenance.entity.User;
import com.soutenance.exception.BadRequestException;
import com.soutenance.exception.ResourceNotFoundException;
import com.soutenance.mapper.AcademicMapper;
import com.soutenance.mapper.UserMapper;
import com.soutenance.repository.AuditLogRepository;
import com.soutenance.repository.DepartmentRepository;
import com.soutenance.repository.FiliereRepository;
import com.soutenance.repository.GradeRepository;
import com.soutenance.repository.JuryRepository;
import com.soutenance.repository.LevelRepository;
import com.soutenance.repository.RoomRepository;
import com.soutenance.repository.SessionRepository;
import com.soutenance.repository.StudentRepository;
import com.soutenance.repository.TeacherRepository;
import com.soutenance.repository.UserRepository;
import com.soutenance.security.CurrentUserService;
import com.soutenance.service.AdminService;
import com.soutenance.util.DateTimeUtil;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final FiliereRepository filiereRepository;
    private final LevelRepository levelRepository;
    private final GradeRepository gradeRepository;
    private final DepartmentRepository departmentRepository;
    private final SessionRepository sessionRepository;
    private final RoomRepository roomRepository;
    private final JuryRepository juryRepository;
    private final AuditLogRepository auditLogRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AcademicMapper academicMapper;
    private final CurrentUserService currentUserService;

    @Override
    @Transactional(readOnly = true)
    public PaginatedResponse<UserResponse> getUsers(int page, int limit, String role) {
        int safeLimit = Math.max(1, limit);
        PageRequest pageRequest = PageRequest.of(Math.max(0, page), safeLimit, Sort.by("lastName").ascending());
        Page<User> result = role == null || role.isBlank()
            ? userRepository.findAll(pageRequest)
            : userRepository.findByRole(Role.fromFrontend(role).frontendValue(), pageRequest);
        return new PaginatedResponse<>(
            result.getContent().stream().map(userMapper::toResponse).toList(),
            result.getTotalElements(),
            result.getTotalPages()
        );
    }

    @Override
    @Transactional
    public UserResponse createUser(UserRequest request) {
        if (userRepository.existsByEmailIgnoreCase(request.email())) {
            throw new BadRequestException("Email deja utilise");
        }
        User created = buildUser(request);
        User saved = userRepository.save(created);
        audit("CREATE", "user", saved.getId(), "Creation utilisateur " + saved.getEmail());
        return userMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public List<UserResponse> bulkCreateUsers(BulkUsersRequest request) {
        Role role = Role.fromFrontend(request.role());
        List<User> created = request.users().stream()
            .map(row -> buildBulkUser(row, role))
            .map(userRepository::save)
            .toList();
        created.forEach(user -> audit("IMPORT", "user", user.getId(), "Import utilisateur " + user.getEmail()));
        return created.stream().map(userMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public UserResponse updateUser(String id, UserRequest request) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Utilisateur introuvable"));
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        if (request.isActive() != null) {
            user.setIsActive(request.isActive());
        }
        if (request.password() != null && !request.password().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.password()));
        }
        if (user instanceof Student student) {
            student.setCne(required(request.cne(), "CNE requis"));
            student.setFiliere(findFiliere(request.filiereId()));
            student.setLevel(findLevel(request.levelId()));
        }
        if (user instanceof Teacher teacher) {
            teacher.setGrade(findGrade(request.gradeId()));
            teacher.setDepartment(findDepartment(request.departmentId()));
        }
        User saved = userRepository.save(user);
        audit("UPDATE", "user", saved.getId(), "Modification utilisateur " + saved.getEmail());
        return userMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void deleteUser(String id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Utilisateur introuvable"));
        userRepository.delete(user);
        audit("DELETE", "user", id, "Suppression utilisateur " + user.getEmail());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentResponse> getDepartments() {
        return departmentRepository.findAll(Sort.by("name")).stream()
            .map(academicMapper::toDepartmentResponse)
            .toList();
    }

    @Override
    @Transactional
    public DepartmentResponse createDepartment(DepartmentRequest request) {
        Department department = new Department();
        applyDepartment(department, request);
        Department saved = departmentRepository.save(department);
        audit("CREATE", "department", saved.getId(), "Creation departement " + saved.getName());
        return academicMapper.toDepartmentResponse(saved);
    }

    @Override
    @Transactional
    public DepartmentResponse updateDepartment(String id, DepartmentRequest request) {
        Department department = findDepartment(id);
        applyDepartment(department, request);
        Department saved = departmentRepository.save(department);
        audit("UPDATE", "department", saved.getId(), "Modification departement " + saved.getName());
        return academicMapper.toDepartmentResponse(saved);
    }

    @Override
    @Transactional
    public void deleteDepartment(String id) {
        Department department = findDepartment(id);
        departmentRepository.delete(department);
        audit("DELETE", "department", id, "Suppression departement " + department.getName());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SessionResponse> getSessions() {
        return sessionRepository.findAll(Sort.by("startDate").descending()).stream()
            .map(academicMapper::toSessionResponse)
            .toList();
    }

    @Override
    @Transactional
    public SessionResponse createSession(SessionRequest request) {
        Session session = new Session();
        applySession(session, request);
        Session saved = sessionRepository.save(session);
        audit("CREATE", "session", saved.getId(), "Creation session " + saved.getName());
        return academicMapper.toSessionResponse(saved);
    }

    @Override
    @Transactional
    public SessionResponse updateSession(String id, SessionRequest request) {
        Session session = sessionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Session introuvable"));
        applySession(session, request);
        Session saved = sessionRepository.save(session);
        audit("UPDATE", "session", saved.getId(), "Modification session " + saved.getName());
        return academicMapper.toSessionResponse(saved);
    }

    @Override
    @Transactional
    public void deleteSession(String id) {
        Session session = sessionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Session introuvable"));
        sessionRepository.delete(session);
        audit("DELETE", "session", id, "Suppression session " + session.getName());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomResponse> getRooms() {
        return roomRepository.findAll(Sort.by("name")).stream()
            .map(academicMapper::toRoomResponse)
            .toList();
    }

    @Override
    @Transactional
    public RoomResponse createRoom(RoomRequest request) {
        Room room = new Room();
        applyRoom(room, request);
        Room saved = roomRepository.save(room);
        audit("CREATE", "room", saved.getId(), "Creation salle " + saved.getName());
        return academicMapper.toRoomResponse(saved);
    }

    @Override
    @Transactional
    public List<RoomResponse> bulkCreateRooms(BulkRoomsRequest request) {
        List<Room> created = request.rooms().stream()
            .map(row -> {
                Room room = new Room();
                room.setName(text(row, "name"));
                room.setBuilding(text(row, "building"));
                room.setCapacity(number(row, "capacity"));
                return roomRepository.save(room);
            })
            .toList();
        created.forEach(room -> audit("IMPORT", "room", room.getId(), "Import salle " + room.getName()));
        return created.stream().map(academicMapper::toRoomResponse).toList();
    }

    @Override
    @Transactional
    public RoomResponse updateRoom(String id, RoomRequest request) {
        Room room = roomRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Salle introuvable"));
        applyRoom(room, request);
        Room saved = roomRepository.save(room);
        audit("UPDATE", "room", saved.getId(), "Modification salle " + saved.getName());
        return academicMapper.toRoomResponse(saved);
    }

    @Override
    @Transactional
    public void deleteRoom(String id) {
        Room room = roomRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Salle introuvable"));
        roomRepository.delete(room);
        audit("DELETE", "room", id, "Suppression salle " + room.getName());
    }

    @Override
    @Transactional(readOnly = true)
    public DashboardStatsDto getStats() {
        return new DashboardStatsDto(
            userRepository.countByRole("student"),
            userRepository.countByRole("teacher"),
            departmentRepository.count(),
            roomRepository.count(),
            sessionRepository.countByStatus("active"),
            juryRepository.count()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuditLogResponse> getAuditLogs() {
        return auditLogRepository.findTop100ByOrderByTimestampDesc().stream()
            .map(academicMapper::toAuditLogResponse)
            .toList();
    }

    private User buildUser(UserRequest request) {
        Role role = Role.fromFrontend(request.role());
        User user = switch (role) {
            case STUDENT -> {
                Student student = new Student();
                student.setCne(required(request.cne(), "CNE requis"));
                student.setFiliere(findFiliere(request.filiereId()));
                student.setLevel(findLevel(request.levelId()));
                yield student;
            }
            case TEACHER -> {
                Teacher teacher = new Teacher();
                teacher.setGrade(findGrade(request.gradeId()));
                teacher.setDepartment(findDepartment(request.departmentId()));
                yield teacher;
            }
            default -> new User();
        };
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setRole(role.frontendValue());
        user.setIsActive(request.isActive() == null ? Boolean.FALSE : request.isActive());
        if (request.password() != null && !request.password().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.password()));
        }
        return user;
    }

    private User buildBulkUser(Map<String, Object> row, Role role) {
        User user = switch (role) {
            case STUDENT -> {
                Student student = new Student();
                student.setCne(text(row, "cne"));
                student.setFiliere(filiereRepository.findByNameIgnoreCase(text(row, "filiereName")).orElseGet(this::firstFiliere));
                student.setLevel(levelRepository.findByNameIgnoreCase(text(row, "levelName")).orElseGet(this::firstLevel));
                yield student;
            }
            case TEACHER -> {
                Teacher teacher = new Teacher();
                teacher.setDepartment(departmentRepository.findAll().stream()
                    .filter(department -> department.getName().equalsIgnoreCase(text(row, "departmentName")))
                    .findFirst()
                    .orElseGet(this::firstDepartment));
                teacher.setGrade(gradeRepository.findByNameIgnoreCase(text(row, "gradeName")).orElseGet(this::firstGrade));
                yield teacher;
            }
            default -> new User();
        };
        user.setFirstName(text(row, "firstName"));
        user.setLastName(text(row, "lastName"));
        user.setEmail(text(row, "email"));
        user.setRole(role.frontendValue());
        user.setIsActive(Boolean.FALSE);
        return user;
    }

    private void applyDepartment(Department department, DepartmentRequest request) {
        department.setName(request.name());
        department.setCode(request.code());
        department.setHead(findTeacher(request.headId()));
    }

    private void applySession(Session session, SessionRequest request) {
        var start = DateTimeUtil.parseDate(request.startDate());
        var end = DateTimeUtil.parseDate(request.endDate());
        if (end.isBefore(start)) {
            throw new BadRequestException("La date de fin doit etre posterieure a la date de debut");
        }
        session.setName(request.name());
        session.setType(request.type());
        session.setStatus(request.status());
        session.setStartDate(start);
        session.setEndDate(end);
    }

    private void applyRoom(Room room, RoomRequest request) {
        room.setName(request.name());
        room.setCapacity(request.capacity());
        room.setBuilding(request.building());
    }

    private Teacher findTeacher(String id) {
        return teacherRepository.findById(required(id, "Enseignant requis"))
            .orElseThrow(() -> new ResourceNotFoundException("Enseignant introuvable"));
    }

    private Department findDepartment(String id) {
        return departmentRepository.findById(required(id, "Departement requis"))
            .orElseThrow(() -> new ResourceNotFoundException("Departement introuvable"));
    }

    private Filiere findFiliere(String id) {
        return filiereRepository.findById(required(id, "Filiere requise"))
            .orElseThrow(() -> new ResourceNotFoundException("Filiere introuvable"));
    }

    private Level findLevel(String id) {
        return levelRepository.findById(required(id, "Niveau requis"))
            .orElseThrow(() -> new ResourceNotFoundException("Niveau introuvable"));
    }

    private Grade findGrade(String id) {
        return gradeRepository.findById(required(id, "Grade requis"))
            .orElseThrow(() -> new ResourceNotFoundException("Grade introuvable"));
    }

    private Filiere firstFiliere() {
        return filiereRepository.findAll().stream().findFirst()
            .orElseThrow(() -> new BadRequestException("Aucune filiere configuree"));
    }

    private Level firstLevel() {
        return levelRepository.findAll().stream().findFirst()
            .orElseThrow(() -> new BadRequestException("Aucun niveau configure"));
    }

    private Grade firstGrade() {
        return gradeRepository.findAll().stream().findFirst()
            .orElseThrow(() -> new BadRequestException("Aucun grade configure"));
    }

    private Department firstDepartment() {
        return departmentRepository.findAll().stream().findFirst()
            .orElseThrow(() -> new BadRequestException("Aucun departement configure"));
    }

    private String required(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new BadRequestException(message);
        }
        return value;
    }

    private String text(Map<String, Object> row, String key) {
        Object value = row.get(key);
        if (value == null || value.toString().isBlank()) {
            throw new BadRequestException("Champ requis: " + key);
        }
        return value.toString().trim();
    }

    private Integer number(Map<String, Object> row, String key) {
        Object value = row.get(key);
        if (value instanceof Number number) {
            return number.intValue();
        }
        try {
            return Integer.parseInt(text(row, key));
        } catch (NumberFormatException ex) {
            throw new BadRequestException("Nombre invalide: " + key);
        }
    }

    private void audit(String action, String entity, String entityId, String details) {
        AuditLog auditLog = new AuditLog();
        auditLog.setAction(action);
        auditLog.setEntity(entity);
        auditLog.setEntityId(entityId);
        auditLog.setAdminEmail(currentAdminEmail());
        auditLog.setDetails(details);
        auditLog.setTimestamp(OffsetDateTime.now());
        auditLogRepository.save(auditLog);
    }

    private String currentAdminEmail() {
        try {
            return currentUserService.user().getEmail();
        } catch (RuntimeException ex) {
            return "system@univ.com";
        }
    }
}
