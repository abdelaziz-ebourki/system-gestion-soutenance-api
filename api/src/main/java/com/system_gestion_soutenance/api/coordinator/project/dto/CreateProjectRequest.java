package com.system_gestion_soutenance.api.coordinator.project.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CreateProjectRequest(
        @NotBlank String title,
        String description,
        @NotBlank String supervisorId,
        List<String> studentIds,
        @NotBlank String defenseType
) {}
