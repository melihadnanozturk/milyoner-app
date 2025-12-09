package org.maoco.milyoner.question.service;

import org.maoco.milyoner.question.data.entity.AdminEntity;
import org.maoco.milyoner.question.data.repository.AdminRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("adminUserDetailsService")
public class AdminUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    public AdminUserDetailsService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminEntity admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Admin bulunamadı"));

        return User.builder()
                .username(admin.getUsername())
                .password(admin.getPassword()) // DB'de Hash'li olmalı
                .roles("ADMIN", "SUPER_USER") // Admin yetkileri
                .build();
    }
}
