package org.maoco.milyoner.gameplay.controller.request;

import lombok.Builder;
import lombok.Value;
import org.maoco.milyoner.gameplay.model.GameStatus;

@Value
@Builder
public class StartGameRequest {

    String username;

    @Builder.Default
    GameStatus gameStatus = GameStatus.START_GAME;
}
