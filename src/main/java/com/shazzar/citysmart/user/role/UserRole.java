package com.shazzar.citysmart.user.role;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.shazzar.citysmart.user.role.UserPermission.*;

public enum UserRole {
    CUSTOMER(Sets.newHashSet(USER_READ, USER_WRITE, FACILITY_READ)),
    FACILITY_OWNER(Sets.newHashSet(USER_READ, USER_WRITE, FACILITY_READ, FACILITY_WRITE));

    private final Set<UserPermission> permissions;

    UserRole(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermission> getPermissions() {
        return permissions;
    }

    public Set<? extends GrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());

        permissions.add(new SimpleGrantedAuthority("Role_" + this.name()));
        return permissions;
    }
}
