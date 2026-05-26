package com.system_gestion_soutenance.api.coordinator.document.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record DefenseIdsRequest(
        List<String> defenseIds,
        String projectId
) {
    public DefenseIdsRequest {
        if (defenseIds == null) defenseIds = List.of();
    }
}
