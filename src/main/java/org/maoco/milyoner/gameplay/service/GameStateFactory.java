package org.maoco.milyoner.gameplay.service;

import org.maoco.milyoner.gameplay.domain.GamePhase;
import org.maoco.milyoner.gameplay.service.handler.GameStateHandler;
import org.maoco.milyoner.gameplay.service.handler.LostGameHandler;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class GameStateFactory {

    private final Map<GamePhase, GameStateHandler> registry = new EnumMap<>(GamePhase.class);

    public GameStateFactory(List<GameStateHandler> gameStateHandlers) {
        gameStateHandlers.forEach(handler -> registry.put(handler.getGameStatus(), handler));
    }

    public GameStateHandler getHandler(GamePhase gamePhase) {
        return registry.getOrDefault(gamePhase, new LostGameHandler());
    }
}
