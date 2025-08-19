package org.maoco.milyoner.gameplay.service.handler;

import org.maoco.milyoner.gameplay.domain.Game;
import org.maoco.milyoner.gameplay.domain.GamePhase;

public interface GameStateHandler {

    GamePhase getGameStatus();

    default Game execute(Game game) {
        return null;
    }
}
