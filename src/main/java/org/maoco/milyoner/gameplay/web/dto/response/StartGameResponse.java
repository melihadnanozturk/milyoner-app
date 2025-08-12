package org.maoco.milyoner.gameplay.web.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.maoco.milyoner.gameplay.domain.GameStatus;

@Getter
@Builder
public class StartGameResponse {

    private String gameId;
    private String playerId;
    private Long questionLevel;
    private GameStatus gameStatus;
}
