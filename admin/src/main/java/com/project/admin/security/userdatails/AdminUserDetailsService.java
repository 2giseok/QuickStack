package com.project.admin.security.userdatails;

import com.project.admin.domain.entity.Admin;
import com.project.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;


    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByLoginId(loginId).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 관리자"));


        return new CustomAdminDetails(admin);

    }
}
