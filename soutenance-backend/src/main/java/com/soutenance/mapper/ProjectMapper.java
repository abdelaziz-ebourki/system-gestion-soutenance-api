package com.soutenance.mapper;

import com.soutenance.dto.Dtos.ProjectResponse;
import com.soutenance.entity.Project;
import com.soutenance.entity.Student;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    public ProjectResponse toResponse(Project project) {
        List<Student> students = project.getStudents().stream()
            .sorted(Comparator.comparing(Student::getLastName).thenComparing(Student::getFirstName))
            .toList();
        return new ProjectResponse(
            project.getId(),
            project.getTitle(),
            project.getDescription(),
            students.stream().map(Student::getId).toList(),
            students.stream().map(Student::fullName).toList(),
            project.getSupervisor() == null ? null : project.getSupervisor().getId(),
            project.getSupervisor() == null ? null : project.getSupervisor().fullName(),
            project.getStatus()
        );
    }
}
