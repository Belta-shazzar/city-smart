package com.shazzar.citysmart.user.role;

public enum UserPermission {
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    FACILITY_READ("facility:read"),
    FACILITY_WRITE("facility:write");

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
