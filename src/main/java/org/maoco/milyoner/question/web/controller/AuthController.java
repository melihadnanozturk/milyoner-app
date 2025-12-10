package org.maoco.milyoner.question.web.controller;

import lombok.RequiredArgsConstructor;
import org.maoco.milyoner.common.security.TokenService;
import org.maoco.milyoner.question.domain.User;
import org.maoco.milyoner.question.service.UserService;
import org.maoco.milyoner.question.web.dto.request.UserRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/panel/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest request) {
        String token = this.createToken(request.username(), request.password());

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, createCookie(token).toString())
                .body("Giriş Başarılı");
    }

    @PostMapping("/admin/register")
    public ResponseEntity<String> adminRegister(@RequestBody UserRequest request) {
        User user = userService.userRegister(request, true);
        String token = this.createToken(user.username(), request.password());
        String cookie = createCookie(token).toString();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie).body(token);
    }

    @PostMapping("/user/register")
    public ResponseEntity<String> userRegister(@RequestBody UserRequest request) {
        User user = userService.userRegister(request, false);
        String token = this.createToken(user.username(), request.password());
        String cookie = createCookie(token).toString();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie).body(token);
    }

    private ResponseCookie createCookie(String token) {
        return ResponseCookie.from("accessToken", token)
                .httpOnly(true)
                .secure(false)        // Localhost: false, Production: true
                .path("/")
                .maxAge(24 * 60 * 60)
                .sameSite("Strict")   // CSRF koruması
                .build();
    }

    private String createToken(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        Map<String, Object> claims = new HashMap<>();
        claims.put("is_admin", true);

        return tokenService.generateToken(authentication.getName(), claims);
    }
}
