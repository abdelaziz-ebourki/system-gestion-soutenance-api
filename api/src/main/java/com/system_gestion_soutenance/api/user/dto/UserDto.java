package com.system_gestion_soutenance.api.user.dto;

import com.system_gestion_soutenance.api.user.entity.User;

public record UserDto(
        String id,
        String email,
        String role,
        String lastName,
        String firstName,
        boolean isActive
) {
    public static UserDto from(User user) {
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getRole().name().toLowerCase(),
                user.getLastName(),
                user.getFirstName(),
                user.isActive()
        );
    }
}
