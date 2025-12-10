package org.maoco.milyoner.question.web.controller;

import lombok.RequiredArgsConstructor;
import org.maoco.milyoner.common.security.TokenService;
import org.maoco.milyoner.question.service.UserService;
import org.maoco.milyoner.question.web.dto.request.UserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        Map<String, Object> claims = new HashMap<>();
        claims.put("is_admin", true);

        String token = tokenService.generateToken(authentication.getName(), claims);

        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/admin/register")
    public ResponseEntity<String> adminRegister(@RequestBody UserRequest request) {
        userService.adminRegisterUser(request);
        return ResponseEntity.ok("Admin kaydı başarıyla oluşturuldu.");
    }

    @PostMapping("/user/register")
    public ResponseEntity<String> userRegister(@RequestBody UserRequest request) {
        userService.userRegisterUser(request);
        return ResponseEntity.ok("User kaydı başarıyla oluşturuldu.");
    }
}
