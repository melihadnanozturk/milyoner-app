package org.maoco.milyoner.gameplay.controller.request;

import lombok.Getter;
import org.maoco.milyoner.gameplay.model.GameStatus;

@Getter
public class GameActionRequest {

    private GameStatus gameStatus;
    private String gameId;
}
