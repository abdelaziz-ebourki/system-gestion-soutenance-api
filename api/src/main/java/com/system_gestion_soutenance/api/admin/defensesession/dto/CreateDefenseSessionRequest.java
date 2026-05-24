package com.system_gestion_soutenance.api.admin.defensesession.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.Map;

public record CreateDefenseSessionRequest(
        @NotBlank String globalSessionId,
        @NotBlank String name,
        @NotBlank String defenseType,
        String status,
        int maxGroupSize,
        int defenseDuration,
        int breakDuration,
        String submissionDeadline,
        Map<String, Integer> evaluationCoefficients,
        String juryRoleTemplateId,
        @NotBlank String startDate,
        @NotBlank String endDate
) {}
