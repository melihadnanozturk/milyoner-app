package org.maoco.milyoner.common.security;

import org.maoco.milyoner.gameplay.data.entity.GameEntity;
import org.maoco.milyoner.gameplay.service.GamePersistenceService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class GameJwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final GamePersistenceService gamePersistenceService;

    public GameJwtConverter(GamePersistenceService gamePersistenceService) {
        this.gamePersistenceService = gamePersistenceService;
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt token) {
        String subject = token.getSubject();
        String gameId = token.getClaims().get("gameId").toString();

        GameEntity gameEntity = gamePersistenceService.findByUsernameAndId(subject, gameId);
        GameSessionContext gameSessionContext = new GameSessionContext(gameEntity.getUsername(), gameEntity.getId());

        return new GameAuthenticationToken(null, gameSessionContext, token);
    }
}
