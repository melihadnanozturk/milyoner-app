package org.maoco.milyoner.common.security;

import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Service
public class TokenService {

    private final JwtEncoder jwtEncoder;

    public TokenService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String generateToken(String username, Map<String, Object> extraClaims) {
        Instant now = Instant.now();

        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder()
                .issuer("self") // Kim imzaladı?
                .issuedAt(now) // Ne zaman?
                .expiresAt(now.plus(1, ChronoUnit.HOURS)) // Ne zaman biter? (1 saat)
                .subject(username); // Kime ait?

        if (extraClaims != null) {
            extraClaims.forEach(claimsBuilder::claim);
        }

        JwtClaimsSet claims = claimsBuilder.build();

        var encoderParameters = JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS256).build(),
                claims
        );

        return this.jwtEncoder.encode(encoderParameters).getTokenValue();
    }
}
