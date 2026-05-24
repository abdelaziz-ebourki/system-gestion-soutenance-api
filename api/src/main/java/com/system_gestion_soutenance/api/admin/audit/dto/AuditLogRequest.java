package com.system_gestion_soutenance.api.admin.audit.dto;

import jakarta.validation.constraints.NotBlank;

public record AuditLogRequest(
        @NotBlank String action,
        @NotBlank String entity,
        @NotBlank String entityId,
        @NotBlank String adminEmail,
        String details
) {}
