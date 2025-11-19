package org.maoco.milyoner.gameplay.web.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.maoco.milyoner.gameplay.service.GameStateEnum;

@Getter
@Setter
@Builder
public class GameResponse {
    private GameStateEnum gameState;
    private String gameId;
    private String playerId;
    private Long questionLevel;
}
