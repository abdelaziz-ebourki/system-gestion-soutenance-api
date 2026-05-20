package com.soutenance.config;

import com.soutenance.entity.AuditLog;
import com.soutenance.entity.ConfigOption;
import com.soutenance.entity.DefenseSettings;
import com.soutenance.entity.Department;
import com.soutenance.entity.Filiere;
import com.soutenance.entity.Grade;
import com.soutenance.entity.Jury;
import com.soutenance.entity.Level;
import com.soutenance.entity.Project;
import com.soutenance.entity.Room;
import com.soutenance.entity.Session;
import com.soutenance.entity.Student;
import com.soutenance.entity.StudentDocument;
import com.soutenance.entity.StudentGroup;
import com.soutenance.entity.Teacher;
import com.soutenance.entity.TeacherEvaluation;
import com.soutenance.entity.User;
import com.soutenance.repository.AuditLogRepository;
import com.soutenance.repository.ConfigOptionRepository;
import com.soutenance.repository.DefenseSettingsRepository;
import com.soutenance.repository.DepartmentRepository;
import com.soutenance.repository.FiliereRepository;
import com.soutenance.repository.GradeRepository;
import com.soutenance.repository.JuryRepository;
import com.soutenance.repository.LevelRepository;
import com.soutenance.repository.ProjectRepository;
import com.soutenance.repository.RoomRepository;
import com.soutenance.repository.SessionRepository;
import com.soutenance.repository.StudentDocumentRepository;
import com.soutenance.repository.StudentGroupRepository;
import com.soutenance.repository.StudentRepository;
import com.soutenance.repository.TeacherEvaluationRepository;
import com.soutenance.repository.TeacherRepository;
import com.soutenance.repository.UserRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final DepartmentRepository departmentRepository;
    private final SessionRepository sessionRepository;
    private final RoomRepository roomRepository;
    private final FiliereRepository filiereRepository;
    private final LevelRepository levelRepository;
    private final GradeRepository gradeRepository;
    private final ProjectRepository projectRepository;
    private final JuryRepository juryRepository;
    private final TeacherEvaluationRepository teacherEvaluationRepository;
    private final StudentGroupRepository studentGroupRepository;
    private final StudentDocumentRepository studentDocumentRepository;
    private final DefenseSettingsRepository defenseSettingsRepository;
    private final ConfigOptionRepository configOptionRepository;
    private final AuditLogRepository auditLogRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner seedDatabase() {
        return args -> {
            seedConfiguration();
            if (userRepository.count() == 0) {
                seedUsersAndAcademicData();
            }
            if (studentDocumentRepository.count() == 0) {
                seedDocuments();
            }
            if (auditLogRepository.count() == 0) {
                seedAuditLogs();
            }
        };
    }

    private void seedConfiguration() {
        if (filiereRepository.count() == 0) {
            List.of("Genie Informatique", "Genie Industriel", "Genie Civil", "Genie Electrique", "Management")
                .forEach(name -> {
                    Filiere item = new Filiere();
                    item.setId("f" + (filiereRepository.count() + 1));
                    item.setName(name);
                    filiereRepository.save(item);
                });
        }
        if (levelRepository.count() == 0) {
            List.of("Licence", "Master", "Doctorat").forEach(name -> {
                Level item = new Level();
                item.setId("n" + (levelRepository.count() + 1));
                item.setName(name);
                levelRepository.save(item);
            });
        }
        if (gradeRepository.count() == 0) {
            List.of("PES", "PH", "PA").forEach(name -> {
                Grade item = new Grade();
                item.setId("g" + (gradeRepository.count() + 1));
                item.setName(name);
                gradeRepository.save(item);
            });
        }
        if (defenseSettingsRepository.count() == 0) {
            DefenseSettings settings = new DefenseSettings();
            settings.setId("default-settings");
            settings.setStartTime(LocalTime.of(8, 0));
            settings.setEndTime(LocalTime.of(18, 0));
            settings.setDefenseDuration(30);
            settings.setBreakDuration(15);
            settings.setGroupCreationStartDate(LocalDate.of(2026, 5, 1));
            settings.setGroupCreationEndDate(LocalDate.of(2026, 6, 20));
            defenseSettingsRepository.save(settings);
        }
        if (configOptionRepository.count() == 0) {
            option("session-type", "Normale");
            option("session-type", "Rattrapage");
            option("session-type", "Speciale");
            option("session-status", "active");
            option("session-status", "draft");
            option("session-status", "archived");
        }
    }

    private void seedUsersAndAcademicData() {
        User admin = baseUser("1", "Mohamed", "Ahmadi", "admin@univ.com", "admin");
        User coordinator = baseUser("2", "Yassin", "Ouchen", "coord@univ.com", "coordinator");

        Teacher teacher1 = teacher("3", "Ben Ali", "Ali", "teacher@univ.com", "g1");
        Teacher teacher2 = teacher("4", "Moussa", "Alami", "moussa@univ.com", "g2");
        userRepository.saveAll(List.of(admin, coordinator, teacher1, teacher2));

        Department info = department("1", "Informatique", "INFO", teacher2);
        Department math = department("2", "Mathematiques", "MATH", teacher1);
        Department physics = department("3", "Physique", "PHYS", teacher1);
        departmentRepository.saveAll(List.of(info, math, physics));

        teacher1.setDepartment(info);
        teacher2.setDepartment(math);
        teacherRepository.saveAll(List.of(teacher1, teacher2));

        Student demo = student("std-demo", "Khalid", "Mohamed", "student@univ.com", "E13000999", "f1", "n2");
        studentRepository.save(demo);
        for (int i = 1; i <= 30; i++) {
            studentRepository.save(student(
                "std-" + i,
                "Prenom" + i,
                "Nom" + i,
                "student" + i + "@univ.com",
                "E13000" + i,
                "f" + (((i - 1) % 5) + 1),
                "n" + (((i - 1) % 3) + 1)
            ));
        }

        Session normal = new Session();
        normal.setId("1");
        normal.setName("Session Normale 2026");
        normal.setType("Normale");
        normal.setStatus("active");
        normal.setStartDate(LocalDate.of(2026, 6, 1));
        normal.setEndDate(LocalDate.of(2026, 6, 30));
        Session makeup = new Session();
        makeup.setId("2");
        makeup.setName("Session Rattrapage 2026");
        makeup.setType("Rattrapage");
        makeup.setStatus("draft");
        makeup.setStartDate(LocalDate.of(2026, 9, 1));
        makeup.setEndDate(LocalDate.of(2026, 9, 15));
        sessionRepository.saveAll(List.of(normal, makeup));

        room("1", "TD-1", 30, "Bloc A");
        room("2", "TD-2", 30, "Bloc A");
        room("3", "Amphi-1", 150, "Bloc B");
        room("4", "TP-1", 20, "Bloc C");
        room("5", "TP-2", 20, "Bloc C");

        Project p1 = project("p1", "Systeme de Gestion des Soutenances", "Plateforme de planification et suivi des jurys.", teacher1, "approved", "std-1", "std-2");
        Project p2 = project("p2", "Application E-learning adaptative", "Personnalisation des parcours selon les performances.", teacher2, "pending", "std-3");
        Project p5 = project("p5", "Portail intelligent de suivi des soutenances", "Interface et services pour suivre planning, documents et evaluations.", teacher1, "approved", "std-demo");
        projectRepository.saveAll(List.of(p1, p2, p5));
        p1.getStudents().forEach(student -> student.setProject(p1));
        p2.getStudents().forEach(student -> student.setProject(p2));
        p5.getStudents().forEach(student -> student.setProject(p5));
        studentRepository.saveAll(p1.getStudents());
        studentRepository.saveAll(p2.getStudents());
        studentRepository.saveAll(p5.getStudents());

        Jury j1 = jury("j1", p1, teacher1, teacher2, teacher1);
        Jury j3 = jury("j3", p5, teacher2, teacher1, teacher2);
        juryRepository.saveAll(List.of(j1, j3));
        createEvaluations(j1);
        createEvaluations(j3);

        StudentGroup group = new StudentGroup();
        group.setId("sg1");
        group.setGroupName("Groupe-1");
        group.setProject(p5);
        group.getMembers().add(demo);
        studentGroupRepository.save(group);
    }

    private void seedDocuments() {
        document("sd1", "Rapport final.pdf", "Rapport", LocalDate.of(2026, 6, 5), "validated", OffsetDateTime.parse("2026-06-03T14:20:00Z"));
        document("sd2", "Presentation finale.pptx", "Presentation", LocalDate.of(2026, 6, 10), "submitted", OffsetDateTime.parse("2026-06-09T18:05:00Z"));
        document("sd3", "Code source.zip", "Archive", LocalDate.of(2026, 6, 10), "missing", null);
    }

    private void seedAuditLogs() {
        audit("1", "LOGIN", "user", "1", "admin@univ.com", "Connexion reussie");
        audit("2", "CREATE", "user", "5", "admin@univ.com", "Creation d'un nouvel etudiant");
    }

    private User baseUser(String id, String firstName, String lastName, String email, String role) {
        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setRole(role);
        user.setPassword(passwordEncoder.encode("1234"));
        user.setIsActive(Boolean.TRUE);
        return user;
    }

    private Teacher teacher(String id, String firstName, String lastName, String email, String gradeId) {
        Teacher teacher = new Teacher();
        teacher.setId(id);
        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);
        teacher.setEmail(email);
        teacher.setRole("teacher");
        teacher.setPassword(passwordEncoder.encode("1234"));
        teacher.setIsActive(Boolean.TRUE);
        teacher.setGrade(gradeRepository.findById(gradeId).orElseThrow());
        return teacher;
    }

    private Student student(String id, String firstName, String lastName, String email, String cne, String filiereId, String levelId) {
        Student student = new Student();
        student.setId(id);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setEmail(email);
        student.setRole("student");
        student.setPassword(passwordEncoder.encode("1234"));
        student.setIsActive(Boolean.TRUE);
        student.setCne(cne);
        student.setFiliere(filiereRepository.findById(filiereId).orElseThrow());
        student.setLevel(levelRepository.findById(levelId).orElseThrow());
        return student;
    }

    private Department department(String id, String name, String code, Teacher head) {
        Department department = new Department();
        department.setId(id);
        department.setName(name);
        department.setCode(code);
        department.setHead(head);
        return department;
    }

    private void room(String id, String name, int capacity, String building) {
        Room room = new Room();
        room.setId(id);
        room.setName(name);
        room.setCapacity(capacity);
        room.setBuilding(building);
        roomRepository.save(room);
    }

    private Project project(String id, String title, String description, Teacher supervisor, String status, String... studentIds) {
        Project project = new Project();
        project.setId(id);
        project.setTitle(title);
        project.setDescription(description);
        project.setSupervisor(supervisor);
        project.setStatus(status);
        project.setStudents(new LinkedHashSet<>(studentRepository.findAllById(List.of(studentIds))));
        return project;
    }

    private Jury jury(String id, Project project, Teacher president, Teacher reporter, Teacher examiner) {
        Jury jury = new Jury();
        jury.setId(id);
        jury.setProject(project);
        jury.setPresident(president);
        jury.setReporter(reporter);
        jury.setExaminer(examiner);
        return jury;
    }

    private void createEvaluations(Jury jury) {
        evaluation(jury.getProject(), jury.getPresident(), "president", "pending", null, null);
        evaluation(jury.getProject(), jury.getReporter(), "reporter", "pending", null, null);
        evaluation(jury.getProject(), jury.getExaminer(), "examiner", "pending", null, null);
        evaluation(jury.getProject(), jury.getProject().getSupervisor(), "supervisor", "submitted", new BigDecimal("17.00"), "Presentation claire et demonstration solide.");
    }

    private void evaluation(Project project, Teacher teacher, String role, String status, BigDecimal score, String comment) {
        TeacherEvaluation evaluation = new TeacherEvaluation();
        evaluation.setDefenseId("td-" + project.getId() + "-" + role);
        evaluation.setProjectTitle(project.getTitle());
        evaluation.setProject(project);
        evaluation.setTeacher(teacher);
        evaluation.setRole(role);
        evaluation.setStatus(status);
        evaluation.setScore(score);
        evaluation.setComment(comment);
        evaluation.setSubmittedAt(score == null ? null : OffsetDateTime.now().minusDays(1));
        teacherEvaluationRepository.save(evaluation);
    }

    private void document(String id, String name, String type, LocalDate deadline, String status, OffsetDateTime submittedAt) {
        StudentDocument document = new StudentDocument();
        document.setId(id);
        document.setName(name);
        document.setType(type);
        document.setDeadline(deadline);
        document.setStatus(status);
        document.setSubmittedAt(submittedAt);
        studentDocumentRepository.save(document);
    }

    private void option(String category, String name) {
        ConfigOption option = new ConfigOption();
        option.setCategory(category);
        option.setName(name);
        configOptionRepository.save(option);
    }

    private void audit(String id, String action, String entity, String entityId, String adminEmail, String details) {
        AuditLog log = new AuditLog();
        log.setId(id);
        log.setAction(action);
        log.setEntity(entity);
        log.setEntityId(entityId);
        log.setAdminEmail(adminEmail);
        log.setDetails(details);
        log.setTimestamp(OffsetDateTime.now());
        auditLogRepository.save(log);
    }
}
