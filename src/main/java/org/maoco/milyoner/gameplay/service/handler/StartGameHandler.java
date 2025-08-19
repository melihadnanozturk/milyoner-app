package org.maoco.milyoner.gameplay.service.handler;

import org.maoco.milyoner.gameplay.domain.Game;
import org.maoco.milyoner.gameplay.domain.GamePhase;
import org.springframework.stereotype.Component;

@Component
public class StartGameHandler implements GameStateHandler {

    private final InProgressGameHandler inProgressGameHandler;

    public StartGameHandler(InProgressGameHandler inProgressGameHandler) {
        this.inProgressGameHandler = inProgressGameHandler;
    }

    @Override
    public GamePhase getGameStatus() {
        return GamePhase.START_GAME;
    }

    @Override
    public Game execute(Game game) {
        game.updateGameState(GamePhase.IN_PROGRESS);
        game.setQuestionLevel(1L);

        return inProgressGameHandler.execute(game);
    }
}
