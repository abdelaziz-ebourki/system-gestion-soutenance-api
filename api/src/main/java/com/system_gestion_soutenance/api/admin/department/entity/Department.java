package com.system_gestion_soutenance.api.admin.department.entity;

import com.system_gestion_soutenance.api.user.entity.Teacher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "department")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    @Id
    private String id;
    private String name;
    private String code;
    @ManyToOne
    @JoinColumn(name = "head_id")
    private Teacher head;
}
