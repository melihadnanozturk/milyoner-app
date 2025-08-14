package org.maoco.milyoner.gameplay.service;

import org.maoco.milyoner.gameplay.domain.Game;
import org.maoco.milyoner.gameplay.domain.GamePhase;

public interface GameStateHandler {

    GamePhase getGameStatus();

    default Game execute(Game game) {
        return null;
    }
}
