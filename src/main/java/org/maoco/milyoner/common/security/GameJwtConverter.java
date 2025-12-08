package org.maoco.milyoner.common.security;


import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GameJwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {


    @Override
    public AbstractAuthenticationToken convert(Jwt source) {

        String subject = source.getSubject();

        Map<String, Object> claims = source.getClaims();
        System.out.println(subject);
        return new JwtAuthenticationToken(source, null, subject);
    }
}
