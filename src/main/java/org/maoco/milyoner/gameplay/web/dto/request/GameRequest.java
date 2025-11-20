package org.maoco.milyoner.gameplay.web.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.maoco.milyoner.gameplay.service.GameStateEnum;

@Getter
@Setter
public class GameRequest {

    //todo: buradaki bazı bilgileri JWT üzerinden alabiliriz
    private String gameId;
    private String playerId;
}