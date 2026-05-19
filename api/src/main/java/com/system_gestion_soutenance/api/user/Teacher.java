package com.system_gestion_soutenance.api.user;

import com.system_gestion_soutenance.api.department.Department;
import com.system_gestion_soutenance.api.grade.Grade;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("TEACHER")
@PrimaryKeyJoinColumn(name = "id")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Teacher extends User {
    @ManyToOne
    @JoinColumn(name = "grade_id")
    private Grade grade;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}
