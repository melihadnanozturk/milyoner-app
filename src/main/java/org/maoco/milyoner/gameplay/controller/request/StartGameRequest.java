package org.maoco.milyoner.gameplay.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;
import org.maoco.milyoner.gameplay.model.GameStatus;

@Value
@Builder
public class StartGameRequest {

    @NotBlank(message = "Username can not be blank")
    String username;

    @Builder.Default
    GameStatus gameStatus = GameStatus.START_GAME;
}
