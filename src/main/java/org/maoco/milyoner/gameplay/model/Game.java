package org.maoco.milyoner.gameplay.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Game {
    private String gameId;
    private String playerId;
    private GameStatus gameStatus;
    private Long questionLevel;
    private Question question;

}
