package com.system_gestion_soutenance.api.coordinator.jury.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateJuryRequest(
        @NotBlank String projectId,
        @NotBlank String presidentId,
        @NotBlank String reporterId,
        @NotBlank String examinerId
) {}
