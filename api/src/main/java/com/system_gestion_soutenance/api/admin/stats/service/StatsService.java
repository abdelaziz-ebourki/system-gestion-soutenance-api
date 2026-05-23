package com.system_gestion_soutenance.api.admin.stats.service;

import com.system_gestion_soutenance.api.admin.department.repository.DepartmentRepository;
import com.system_gestion_soutenance.api.admin.defensesession.repository.DefenseSessionRepository;
import com.system_gestion_soutenance.api.admin.room.repository.RoomRepository;
import com.system_gestion_soutenance.api.admin.session.repository.SessionRepository;
import com.system_gestion_soutenance.api.user.repository.StudentRepository;
import com.system_gestion_soutenance.api.user.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StatsService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final DepartmentRepository departmentRepository;
    private final RoomRepository roomRepository;
    private final SessionRepository sessionRepository;
    private final DefenseSessionRepository defenseSessionRepository;

    public StatsService(StudentRepository studentRepository,
                         TeacherRepository teacherRepository,
                         DepartmentRepository departmentRepository,
                         RoomRepository roomRepository,
                         SessionRepository sessionRepository,
                         DefenseSessionRepository defenseSessionRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.departmentRepository = departmentRepository;
        this.roomRepository = roomRepository;
        this.sessionRepository = sessionRepository;
        this.defenseSessionRepository = defenseSessionRepository;
    }

    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalStudents", studentRepository.count());
        stats.put("totalTeachers", teacherRepository.count());
        stats.put("totalDepartments", departmentRepository.count());
        stats.put("totalRooms", roomRepository.count());
        stats.put("activeSessions", sessionRepository.count());
        stats.put("upcomingDefenses", defenseSessionRepository.count());
        return stats;
    }
}
