package org.maoco.milyoner.gameplay.web.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.maoco.milyoner.gameplay.domain.GamePhase;

@Getter
@Setter
@Builder
public class GameResponse {
    private GamePhase gamePhase;
    private String gameId;
    private String playerId;
    private Long questionLevel;
}
