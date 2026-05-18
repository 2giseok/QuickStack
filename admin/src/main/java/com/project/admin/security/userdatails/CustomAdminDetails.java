package com.project.admin.security.userdatails;

import com.project.admin.domain.entity.Admin;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.swing.plaf.PanelUI;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class CustomAdminDetails implements UserDetails {

    private final Admin admin;


    @Override
    public @Nullable String getPassword() {
        return admin.getPassword();
    }

    @Override
    public @Nullable String getUsername() {
        return admin.getLoginId();
    }

    public Long getAdminId() {
        return admin.getId();
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }


}
