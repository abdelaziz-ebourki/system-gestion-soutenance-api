package com.soutenance.mapper;

import com.soutenance.dto.Dtos.UserResponse;
import com.soutenance.entity.Student;
import com.soutenance.entity.Teacher;
import com.soutenance.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        String cne = null;
        String filiereId = null;
        String levelId = null;
        String projectId = null;
        String gradeId = null;
        String departmentId = null;

        if (user instanceof Student student) {
            cne = student.getCne();
            filiereId = student.getFiliere() == null ? null : student.getFiliere().getId();
            levelId = student.getLevel() == null ? null : student.getLevel().getId();
            projectId = student.getProject() == null ? null : student.getProject().getId();
        }

        if (user instanceof Teacher teacher) {
            gradeId = teacher.getGrade() == null ? null : teacher.getGrade().getId();
            departmentId = teacher.getDepartment() == null ? null : teacher.getDepartment().getId();
        }

        return new UserResponse(
            user.getId(),
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getRole(),
            user.getIsActive(),
            cne,
            filiereId,
            levelId,
            projectId,
            gradeId,
            departmentId
        );
    }
}
