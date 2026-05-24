package com.system_gestion_soutenance.api.coordinator.jury.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CreateJuryRequest(
        @NotBlank String projectId,
        @NotBlank String templateId,
        @NotEmpty List<MemberEntry> members
) {
    public record MemberEntry(@NotBlank String teacherId, @NotBlank String roleName) {}
}
