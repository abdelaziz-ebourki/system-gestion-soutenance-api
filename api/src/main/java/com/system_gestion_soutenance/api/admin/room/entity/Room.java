package com.system_gestion_soutenance.api.admin.room.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.system_gestion_soutenance.api.admin.department.entity.Department;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @JsonIgnoreProperties("head")
    private Department department;
}
