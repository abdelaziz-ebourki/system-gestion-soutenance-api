package com.soutenance.entity;

import java.util.Locale;

public enum Role {
    ADMIN,
    COORDINATOR,
    TEACHER,
    STUDENT;

    public static Role fromFrontend(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Role is required");
        }
        return Role.valueOf(value.trim().toUpperCase(Locale.ROOT));
    }

    public String frontendValue() {
        return name().toLowerCase(Locale.ROOT);
    }
}
