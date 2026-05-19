package com.system_gestion_soutenance.api.auth;

import jakarta.validation.constraints.NotBlank;

public record VerifyRequest(
        @NotBlank String token,
        @NotBlank String password
) {}
