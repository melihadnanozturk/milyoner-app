package org.maoco.milyoner.gameplay.service.handler;

import org.maoco.milyoner.gameplay.domain.Game;
import org.maoco.milyoner.gameplay.service.GameService;

@GameStateHandler(GameStateEnum.WON)
public class WonGameState implements GameState {

    private final GameService gameService;

    public WonGameState(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public Game process(Game game) {
//        game.setGameId(game.getGameId());
//        game.setPlayerId(game.getPlayerId());
//        System.out.println("WON game");

        return gameService.won(game);
    }

    @Override
    public String getDesc() {
        return "";
    }
}
