package org.maoco.milyoner.common.security;

import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

//@Service
@Component
public class JwtUtil {

    private final JwtEncoder jwtEncoder;

    public JwtUtil(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String generateToken(String username, Map<String, Object> extraClaims) {
        Instant now = Instant.now();

        // 1. Token içeriğini (Payload/Claims) hazırlıyoruz
        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder()
                .issuer("self") // Kim imzaladı?
                .issuedAt(now) // Ne zaman?
                .expiresAt(now.plus(1, ChronoUnit.HOURS)) // Ne zaman biter? (1 saat)
                .subject(username) // Kime ait?
                .claim("roles", List.of("ROLE_USER")); // Standart roller

        // Varsa ekstra bilgileri (oyun ID vb.) ekle
        if (extraClaims != null) {
            extraClaims.forEach(claimsBuilder::claim);
        }

        JwtClaimsSet claims = claimsBuilder.build();

        // 2. İmzalama algoritmasını (HS256) belirtiyoruz
        var encoderParameters = JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS256).build(),
                claims
        );

        // 3. Token'ı oluştur ve String olarak dön
        return this.jwtEncoder.encode(encoderParameters).getTokenValue();
    }
}
