package org.maoco.milyoner.gameplay.web.dto.request;

import lombok.Getter;
import org.maoco.milyoner.gameplay.domain.GamePhase;

@Getter
public class GameActionRequest {

    private GamePhase gamePhase;
    private String gameId;
}
