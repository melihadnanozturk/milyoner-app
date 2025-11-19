package org.maoco.milyoner.gameplay.web.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.maoco.milyoner.gameplay.service.GameStateEnum;

@Getter
@Builder
public class StartGameResponse {

    //todo: extends Game ?
    private String gameId;
    private String playerId;
    private Long questionLevel;
    private GameStateEnum gameState;
}
