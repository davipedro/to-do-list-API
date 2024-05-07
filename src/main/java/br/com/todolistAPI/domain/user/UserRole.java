package br.com.todolistAPI.domain.user;

import java.util.Arrays;

public enum UserRole {
    ROOT("root"),
    ADMIN("admin"),
    USER("user");

    private final String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public static boolean isRole(UserRole role){
        return Arrays.stream(UserRole.values())
                .anyMatch(userRole -> userRole.getRole().equalsIgnoreCase(role.getRole()));
    }
}