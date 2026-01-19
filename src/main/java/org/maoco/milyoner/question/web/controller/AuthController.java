package org.maoco.milyoner.question.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.maoco.milyoner.common.annotation.RateLimit;
import org.maoco.milyoner.common.security.TokenService;
import org.maoco.milyoner.question.domain.User;
import org.maoco.milyoner.question.service.UserService;
import org.maoco.milyoner.question.web.dto.request.UserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/panel/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @RateLimit(limit = 5, windowMinutes = 1)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserRequest request) {
        String token = this.createToken(request.username(), request.password());

        return ResponseEntity
                .ok()
                .body(token);
    }

    @RateLimit(limit = 3, windowMinutes = 60)
    @PostMapping("/admin/register")
    public ResponseEntity<String> adminRegister(@RequestBody @Valid UserRequest request) {
        User user = userService.userRegister(request, true);
        String token = this.createToken(user.username(), request.password());

        return ResponseEntity.ok().body(token);
    }

    @RateLimit(limit = 3, windowMinutes = 60)
    @PostMapping("/user/register")
    public ResponseEntity<String> userRegister(@RequestBody @Valid UserRequest request) {
        User user = userService.userRegister(request, false);
        String token = this.createToken(user.username(), request.password());

        return ResponseEntity.ok().body(token);
    }

    private String createToken(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);

        return tokenService.generateToken(authentication.getName(), claims);
    }
}
