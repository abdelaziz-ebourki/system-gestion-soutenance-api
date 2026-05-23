package com.system_gestion_soutenance.api.admin.config.juryrole.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateJuryRoleTemplateRequest(
        @NotBlank String name
) {}
