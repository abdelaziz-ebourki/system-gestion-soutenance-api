package com.system_gestion_soutenance.api.coordinator.document.dto;

import jakarta.validation.constraints.NotBlank;

public record SessionRequest(
        @NotBlank String defenseSessionId
) {}
