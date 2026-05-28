package com.system_gestion_soutenance.api.admin.defensesession.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

public record CreateDefenseSessionRequest(
        @NotNull Long globalSessionId,
        @NotBlank String name,
        @NotBlank String defenseType,
        String status,
        int maxGroupSize,
        int defenseDuration,
        int breakDuration,
        String submissionDeadline,
        Map<String, Integer> evaluationCoefficients,
        Long juryRoleTemplateId,
        @NotBlank String startDate,
        @NotBlank String endDate
) {}
