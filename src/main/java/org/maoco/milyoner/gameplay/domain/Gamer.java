package org.maoco.milyoner.gameplay.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.maoco.milyoner.gameplay.service.GameStateEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Gamer {

    private String username;
    private String playerId;
    private String gameId;
    private Long questionLevel;
    private GameStateEnum gameState;
}
