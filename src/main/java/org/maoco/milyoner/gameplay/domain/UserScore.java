package org.maoco.milyoner.gameplay.domain;

import lombok.Getter;
import lombok.Setter;
import org.maoco.milyoner.gameplay.service.GameStateEnum;

@Getter
public class UserScore {

    public UserScore(String username, Long score,GameStateEnum gameState) {
        this.score = score;
        this.username = username;
        this.gameState = gameState;
    }

    //todo: simdilik questionLevel - sonrasında her sorunun puan karsiligi olacaktir
    private Long score;

    private String username;

    @Setter
    private String message;

    private GameStateEnum gameState;

}
