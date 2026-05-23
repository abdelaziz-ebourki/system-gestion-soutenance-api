package com.system_gestion_soutenance.api.admin.session.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateSessionRequest(
        @NotBlank String name,
        @NotBlank String type,
        @NotBlank String status,
        @NotBlank String startDate,
        @NotBlank String endDate
) {}
