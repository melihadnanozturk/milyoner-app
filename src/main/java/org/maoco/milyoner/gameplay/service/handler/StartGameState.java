package org.maoco.milyoner.gameplay.service.handler;

import org.maoco.milyoner.gameplay.domain.Game;

@GameStateHandler(GameStateEnum.START_GAME)
public class StartGameState implements GameState {

    @Override
    public Game process(Game game) {
//        gameService.startGame(game.)
        game.setQuestionLevel(1L);
        game.setGameState(GameStateEnum.IN_PROGRESS);

        return game;
    }

    @Override
    public String getDesc() {
        return "";
    }
}
