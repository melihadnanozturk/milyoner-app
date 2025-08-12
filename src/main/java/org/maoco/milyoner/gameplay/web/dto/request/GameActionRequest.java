package org.maoco.milyoner.gameplay.web.dto.request;

import lombok.Getter;
import org.maoco.milyoner.gameplay.domain.GameStatus;

@Getter
public class GameActionRequest {

    private GameStatus gameStatus;
    private String gameId;
}
