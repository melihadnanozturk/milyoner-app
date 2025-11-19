package org.maoco.milyoner.gameplay.service.handler;

import org.maoco.milyoner.gameplay.domain.Game;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GameStateFactory {

    private final Map<GameStateEnum, GameState> gameStateMap = new HashMap<>();

    public GameStateFactory(List<GameState> gameStates) {
        for (GameState gameState : gameStates) {
            GameStateHandler annotation = gameState.getClass().getAnnotation(GameStateHandler.class);
            if (annotation != null) {
                gameStateMap.put(annotation.value(), gameState);
            }
        }
    }

    public Game process(Game game) {
        GameState gameState = gameStateMap.get(game.getGameState());
        return gameState.process(game);
    }
}
