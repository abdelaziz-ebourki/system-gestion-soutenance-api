package com.system_gestion_soutenance.api.coordinator.group.entity;

import com.system_gestion_soutenance.api.coordinator.project.entity.Project;
import com.system_gestion_soutenance.api.user.entity.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "coordinator_group")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Group {

    @Id
    private String id;

    @Column(name = "group_name")
    private String groupName;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToMany
    @JoinTable(name = "group_members",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<Student> students;

    @Column(name = "session_id")
    private String sessionId;
}
