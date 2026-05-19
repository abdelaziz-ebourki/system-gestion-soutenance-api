package com.system_gestion_soutenance.api.auth;

public record LoginResponse(
        UserDto user,
        String token,
        long expiresAt
) {}
