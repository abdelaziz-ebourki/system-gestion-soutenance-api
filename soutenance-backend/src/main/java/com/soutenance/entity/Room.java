package com.soutenance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
    name = "rooms",
    indexes = {
        @Index(name = "idx_rooms_name", columnList = "name", unique = true),
        @Index(name = "idx_rooms_building", columnList = "building")
    }
)
public class Room extends BaseEntity {

    @Column(nullable = false, unique = true, length = 80)
    private String name;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false, length = 80)
    private String building;
}
