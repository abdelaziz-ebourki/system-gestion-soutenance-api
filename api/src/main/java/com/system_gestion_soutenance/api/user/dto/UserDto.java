package com.system_gestion_soutenance.api.user.dto;

import com.system_gestion_soutenance.api.user.entity.Student;
import com.system_gestion_soutenance.api.user.entity.Teacher;
import com.system_gestion_soutenance.api.user.entity.User;

public record UserDto(
        String id,
        String email,
        String role,
        String lastName,
        String firstName,
        boolean isActive,
        String cne,
        String majorId,
        String majorName,
        String levelId,
        String levelName,
        String gradeId,
        String gradeName,
        String departmentId,
        String departmentName
) {
    public static UserDto from(User user) {
        String cne = null;
        String majorId = null;
        String majorName = null;
        String levelId = null;
        String levelName = null;
        String gradeId = null;
        String gradeName = null;
        String departmentId = null;
        String departmentName = null;

        if (user instanceof Student student) {
            cne = student.getCne();
            if (student.getMajor() != null) {
                majorId = student.getMajor().getId();
                majorName = student.getMajor().getName();
            }
            if (student.getLevel() != null) {
                levelId = student.getLevel().getId();
                levelName = student.getLevel().getName();
            }
        } else if (user instanceof Teacher teacher) {
            if (teacher.getGrade() != null) {
                gradeId = teacher.getGrade().getId();
                gradeName = teacher.getGrade().getName();
            }
            if (teacher.getDepartment() != null) {
                departmentId = teacher.getDepartment().getId();
                departmentName = teacher.getDepartment().getName();
            }
        }

        return new UserDto(
                user.getId(), user.getEmail(), user.getRole().name().toLowerCase(),
                user.getLastName(), user.getFirstName(), user.isActive(),
                cne, majorId, majorName, levelId, levelName,
                gradeId, gradeName, departmentId, departmentName
        );
    }
}
