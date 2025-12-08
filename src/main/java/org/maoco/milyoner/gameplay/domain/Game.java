package org.maoco.milyoner.gameplay.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.maoco.milyoner.gameplay.data.entity.GameEntity;
import org.maoco.milyoner.gameplay.service.GameState;

@Getter
@Setter
public class Game {

    private String gameId;
    private String username;
    private GameState gameState;
    private Long questionLevel;

    //todo: dışarıya alınacak
    private Question question;

    @Builder
    public Game(String gameId,
                GameState gameState,
                Long questionLevel,
                String username) {
        this.gameId = gameId;
        this.questionLevel = questionLevel;
        this.gameState = gameState;
        this.username = username;
    }

    public static Game buildGameFromGamerEntity(GameEntity gameEntity) {
        return Game.builder()
                .gameId(gameEntity.getId())
                .gameState(gameEntity.getGameState())
                .questionLevel(gameEntity.getQuestionLevel())
                .build();
    }
}
