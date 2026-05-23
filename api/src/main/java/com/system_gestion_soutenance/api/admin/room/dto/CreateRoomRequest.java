package com.system_gestion_soutenance.api.admin.room.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CreateRoomRequest(
        @NotBlank String name,
        @Positive int capacity,
        @NotBlank String departmentId
) {}
