package com.system_gestion_soutenance.api.coordinator.unavailability.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "unavailability")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Unavailability {

    @Id
    private String id;

    @Column(name = "teacher_id", nullable = false)
    private String teacherId;

    @Column(nullable = false)
    private String date;

    @ElementCollection
    @CollectionTable(name = "unavailability_slots", joinColumns = @JoinColumn(name = "unavailability_id"))
    @Column(name = "slot")
    private List<String> slots;
}
