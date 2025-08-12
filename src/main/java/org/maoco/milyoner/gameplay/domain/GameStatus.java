package org.maoco.milyoner.gameplay.domain;

import lombok.Getter;

@Getter
public enum GameStatus {

    START_GAME("in_Progress"),
    IN_PROGRESS("in_Progress"),
    WON("won"),
    QUIT("quit"),
    LOSE("lose");

    private final String status;

    GameStatus(String status) {
        this.status = status;
    }
}
