package org.odc.demo.Datas.Adapter;

import org.odc.demo.Datas.Entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsAdapter implements UserDetails {

    private final UserEntity userEntity;

    public UserDetailsAdapter(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(userEntity.getRole().getLibelle()));

    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Adjust this according to your UserEntity logic
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Adjust this according to your UserEntity logic
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Adjust this according to your UserEntity logic
    }

    @Override
    public boolean isEnabled() {
        return userEntity.isEnabled(); // Assuming UserEntity has an isEnabled field
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }
}