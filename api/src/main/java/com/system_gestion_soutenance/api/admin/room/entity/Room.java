package com.system_gestion_soutenance.api.admin.room.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.system_gestion_soutenance.api.admin.department.entity.Department;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "room")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
    private String id;
    private String name;
    private int capacity;
    @ManyToOne
    @JoinColumn(name = "department_id")
    @JsonIgnore
    private Department department;

    public String getDepartmentId() {
        return department != null ? department.getId() : null;
    }
}
