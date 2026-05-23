package com.system_gestion_soutenance.api.coordinator.group.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CreateGroupRequest(
        @NotBlank String projectId,
        List<String> studentIds,
        String sessionId
) {}
