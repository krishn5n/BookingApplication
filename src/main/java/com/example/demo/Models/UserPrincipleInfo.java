package com.example.demo.Models;

import com.example.demo.Tables.User;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

//An implementation of UserDetails for security -> Nee
@NoArgsConstructor
public class UserPrincipleInfo implements UserDetails {
    private final BCryptPasswordEncoder passwordHasher = new BCryptPasswordEncoder(12);
    private String username;
    private String password;
    private String role;


    public UserPrincipleInfo(User obj) {
        this.password = obj.getPasswordHash();
        this.username = obj.getEmail();
        this.role = obj.getRole().name();

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public @Nullable String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
