package org.maoco.milyoner.gameplay.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.maoco.milyoner.gameplay.model.GameStatus;

@Getter
@Setter
@Builder
public class GameResponse {
    private GameStatus gameStatus;
    private String gameId;
    private String playerId;
    private Long questionLevel;
}
