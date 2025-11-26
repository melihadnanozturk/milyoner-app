package org.maoco.milyoner.gameplay.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.maoco.milyoner.gameplay.data.entity.GamerEntity;
import org.maoco.milyoner.gameplay.service.GameStateEnum;

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

    public static Game buildGameFromGamerEntity(GamerEntity gamerEntity) {
        return Game.builder()
                .playerId(gamerEntity.getId())
                .gameId(gamerEntity.getGameId())
                .gameState(gamerEntity.getGameState())
                .questionLevel(gamerEntity.getQuestionLevel())
                .build();
    }
}
