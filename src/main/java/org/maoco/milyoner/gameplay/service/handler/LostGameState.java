package org.maoco.milyoner.gameplay.service.handler;

import org.maoco.milyoner.gameplay.domain.Game;
import org.maoco.milyoner.gameplay.service.GameService;

@GameStateHandler(GameStateEnum.LOST)
public class LostGameState implements GameState {

    private final GameService gameService;

    public LostGameState(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public Game process(Game game) {
//        game.setPlayerId(game.getPlayerId());
//        game.setGameId(game.getGameId());
//        System.out.println("Lost game");

        return gameService.lost(game);
    }

    @Override
    public String getDesc() {
        return "";
    }
}
