package org.maoco.milyoner.gameplay.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.maoco.milyoner.gameplay.service.handler.GameStateEnum;
import org.maoco.milyoner.gameplay.web.dto.request.GameRequest;

@Getter
@Setter
public class Game {

    private String gameId;
    private String playerId;
    private GameStateEnum gameState;
    private Long questionLevel;
    private Question question;

    @Builder
    public Game(String gameId, String playerId, GameStateEnum gameState, Long questionLevel, Question question) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.questionLevel = questionLevel;
        this.question = question;
        this.gameState = gameState;
    }

    public void updateGameState(GameStateEnum gameState) {
        this.setGameState(gameState);
    }

    public static Game buildGameFromRequest(GameRequest request) {
        return Game.builder()
                .playerId(request.getPlayerId())
                .gameId(request.getGameId())
                .questionLevel(request.getQuestionLevel())
                .gameState(request.getGameState())
                .build();
    }
}
