package org.maoco.milyoner.gameplay.controller.request;

import lombok.Getter;
import lombok.Setter;
import org.maoco.milyoner.gameplay.model.GameStatus;

@Getter
@Setter
public class GameRequest {
    private GameStatus gameStatus;
    private String gameId;
    private String playerId;
    private Long questionLevel;
}