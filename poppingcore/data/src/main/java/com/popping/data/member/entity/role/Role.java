package com.popping.data.member.entity.role;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private final String roleName;

    Role(String role) {
        this.roleName = role;
    }
}
