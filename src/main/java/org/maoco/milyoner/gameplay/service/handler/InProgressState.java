package org.maoco.milyoner.gameplay.service.handler;

import org.maoco.milyoner.gameplay.domain.Game;
import org.maoco.milyoner.gameplay.service.GameService;

@GameStateHandler(GameStateEnum.IN_PROGRESS)
public class InProgressState implements GameState {

    public final GameService gameService;

    public InProgressState(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public Game process(Game game) {
        return gameService.inProgress(game);
    }

    @Override
    public String getDesc() {
        return "";
    }
}
