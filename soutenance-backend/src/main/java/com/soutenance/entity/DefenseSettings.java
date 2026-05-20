package com.soutenance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "defense_settings")
public class DefenseSettings extends BaseEntity {

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "defense_duration", nullable = false)
    private Integer defenseDuration;

    @Column(name = "break_duration", nullable = false)
    private Integer breakDuration;

    @Column(name = "group_creation_start_date", nullable = false)
    private LocalDate groupCreationStartDate;

    @Column(name = "group_creation_end_date", nullable = false)
    private LocalDate groupCreationEndDate;

    @Column(name = "schedule_json", columnDefinition = "TEXT")
    private String scheduleJson;
}
