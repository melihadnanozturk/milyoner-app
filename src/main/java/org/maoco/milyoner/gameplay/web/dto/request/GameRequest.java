package org.maoco.milyoner.gameplay.web.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.maoco.milyoner.gameplay.service.handler.GameStateEnum;

@Getter
@Setter
public class GameRequest {
    private GameStateEnum gameState;
    private String gameId;
    private String playerId;
    private Long questionLevel;
}