package org.maoco.milyoner.common.security;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;

public class GameAuthenticationToken extends AbstractAuthenticationToken {

    @Getter
    private final GameSessionContext gameSessionContext;
    private final Jwt jwt;

    public GameAuthenticationToken(Collection<? extends GrantedAuthority> authorities, GameSessionContext gameSessionContext, Jwt jwt) {
        super(authorities);
        this.gameSessionContext = gameSessionContext;
        this.jwt = jwt;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return jwt;
    }

    @Override
    public Object getPrincipal() {
        return gameSessionContext;
    }

}
