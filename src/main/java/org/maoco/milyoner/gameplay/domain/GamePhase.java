package org.maoco.milyoner.gameplay.domain;

import lombok.Getter;

@Getter
public enum GamePhase {

    START_GAME("in_Progress"),
    IN_PROGRESS("in_Progress"),
    WON("won"),
    QUIT("quit"),
    LOST("lose");

    private final String status;

    GamePhase(String status) {
        this.status = status;
    }
}
