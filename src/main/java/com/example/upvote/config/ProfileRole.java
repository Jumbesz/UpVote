package com.example.upvote.config;

public enum ProfileRole {
    ROLE_GUEST("GUEST"),
    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN");

    private final String role;

    ProfileRole(String role) {
        this.role = role;
    }
}
