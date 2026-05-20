package com.soutenance.mapper;

import com.soutenance.dto.Dtos.TeacherDefenseResponse;
import com.soutenance.dto.Dtos.TeacherEvaluationResponse;
import com.soutenance.entity.Project;
import com.soutenance.entity.Student;
import com.soutenance.entity.TeacherEvaluation;
import com.soutenance.util.DateTimeUtil;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class TeacherMapper {

    public TeacherEvaluationResponse toEvaluationResponse(TeacherEvaluation evaluation) {
        return new TeacherEvaluationResponse(
            evaluation.getId(),
            evaluation.getDefenseId(),
            evaluation.getProjectTitle(),
            studentNames(evaluation.getProject()),
            evaluation.getRole(),
            evaluation.getScore(),
            evaluation.getComment(),
            evaluation.getStatus(),
            DateTimeUtil.offset(evaluation.getSubmittedAt())
        );
    }

    public TeacherDefenseResponse toDefenseResponse(
        String id,
        Project project,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime,
        String roomName,
        String role,
        String status
    ) {
        return new TeacherDefenseResponse(
            id,
            project.getId(),
            project.getTitle(),
            studentNames(project),
            DateTimeUtil.date(date),
            DateTimeUtil.time(startTime),
            DateTimeUtil.time(endTime),
            roomName,
            role,
            status
        );
    }

    private List<String> studentNames(Project project) {
        if (project == null) {
            return List.of();
        }
        return project.getStudents().stream()
            .sorted(Comparator.comparing(Student::getLastName).thenComparing(Student::getFirstName))
            .map(Student::fullName)
            .toList();
    }
}
