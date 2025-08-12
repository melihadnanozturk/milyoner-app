package org.maoco.milyoner.gameplay.web.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.maoco.milyoner.gameplay.domain.GameStatus;

@Getter
@Setter
public class GameRequest {
    private GameStatus gameStatus;
    private String gameId;
    private String playerId;
    private Long questionLevel;
}