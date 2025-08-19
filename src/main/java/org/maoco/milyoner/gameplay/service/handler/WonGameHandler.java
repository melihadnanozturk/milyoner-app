package org.maoco.milyoner.gameplay.service.handler;

import org.maoco.milyoner.gameplay.domain.Game;
import org.maoco.milyoner.gameplay.domain.GamePhase;
import org.springframework.stereotype.Component;

@Component
public class WonGameHandler implements GameStateHandler {

    @Override
    public GamePhase getGameStatus() {
        return GamePhase.WON;
    }

    @Override
    public Game execute(Game game) {
        game.setGameId(game.getGameId());
        game.setPlayerId(game.getPlayerId());
        return game;
    }
}
