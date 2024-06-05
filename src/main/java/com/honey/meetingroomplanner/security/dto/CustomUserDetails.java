package com.honey.meetingroomplanner.security.dto;

import com.honey.meetingroomplanner.user.entity.RoleType;
import com.honey.meetingroomplanner.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Getter
public class CustomUserDetails implements UserDetails {
    private final String username;

    private final String password;

    private final String name;

    private final RoleType roleType;

    public static CustomUserDetails from(User user) {
        return CustomUserDetails.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .name(user.getName())
                .roleType(user.getRoleType())
                .build();
    }

    @Builder
    public CustomUserDetails(String username, String password, String name, RoleType roleType) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.roleType = roleType;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(new SimpleGrantedAuthority(roleType.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
