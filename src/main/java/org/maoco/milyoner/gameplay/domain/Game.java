package org.maoco.milyoner.gameplay.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.maoco.milyoner.gameplay.service.handler.GameStateHandler;

@Getter
@Setter
public class Game {
    private String gameId;
    private String playerId;
    private GamePhase gamePhase;
    private Long questionLevel;
    private Question question;
    private GameStateHandler currentState;

    @Builder
    public Game(String gameId, String playerId, GamePhase gamePhase, Long questionLevel, Question question) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.questionLevel = questionLevel;
        this.question = question;
        this.gamePhase = gamePhase;
    }

    public void updateGameState(GamePhase newPhase, GameStateHandler stateHandler) {
        this.setGamePhase(newPhase);
        this.setCurrentState(stateHandler);
    }

    public void updateGameState(GamePhase newPhase) {
        this.setGamePhase(newPhase);
    }

}
