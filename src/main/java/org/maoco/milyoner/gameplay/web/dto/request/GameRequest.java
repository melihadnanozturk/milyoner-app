package org.maoco.milyoner.gameplay.web.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.maoco.milyoner.gameplay.domain.GamePhase;

@Getter
@Setter
public class GameRequest {
    private GamePhase gamePhase;
    private String gameId;
    private String playerId;
    private Long questionLevel;
}