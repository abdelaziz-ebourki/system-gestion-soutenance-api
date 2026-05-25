package com.system_gestion_soutenance.api.admin.department.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateDepartmentRequest(
        @NotBlank String name,
        @NotBlank String code,
        String headId,
        @NotBlank String facultyId
) {}
