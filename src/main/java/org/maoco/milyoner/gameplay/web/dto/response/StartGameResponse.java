package org.maoco.milyoner.gameplay.web.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.maoco.milyoner.gameplay.domain.GamePhase;

@Getter
@Builder
public class StartGameResponse {

    private String gameId;
    private String playerId;
    private Long questionLevel;
    private GamePhase gamePhase;
}
