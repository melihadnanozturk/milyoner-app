package org.maoco.milyoner.gameplay.service.handler;

import org.maoco.milyoner.gameplay.domain.Game;

public interface GameState {

    Game process(Game game);
    String getDesc();
}
