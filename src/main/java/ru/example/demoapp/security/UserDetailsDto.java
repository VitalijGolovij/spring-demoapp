package ru.example.demoapp.security;

import org.springframework.security.core.GrantedAuthority;
import ru.example.demoapp.model.User;

import java.util.Collection;


public class UserDetailsDto implements org.springframework.security.core.userdetails.UserDetails {
    private final User user;

    public UserDetailsDto(User user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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

    public User getUser(){
        return this.user;
    }
}
