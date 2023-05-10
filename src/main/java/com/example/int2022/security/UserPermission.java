package com.example.int2022.security;

public enum UserPermission {
    CUSTOMER_READ("customer:read"),
    CUSTOMER_WRITE("customer:write"),
    MOVIE_READ("movie:read"),
    MOVIE_WRITE("movie:write");

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
