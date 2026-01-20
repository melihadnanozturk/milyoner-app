package org.maoco.milyoner.question.service;

import org.maoco.milyoner.common.error.AuthError;
import org.maoco.milyoner.question.data.entity.UserEntity;
import org.maoco.milyoner.question.data.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("adminUserDetailsService")
public class AdminUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public AdminUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity admin = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(AuthError.USER_NOT_FOUND.getMessage()));

        return User.builder()
                .username(admin.getUsername())
                .password(admin.getPassword())
                .roles(admin.getUserRoles().toString())
                .build();
    }
}
