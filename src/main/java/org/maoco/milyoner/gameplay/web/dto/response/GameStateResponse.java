package org.maoco.milyoner.gameplay.web.dto.response;


import lombok.Getter;
import org.maoco.milyoner.gameplay.domain.GameStatus;

@Getter
public class GameStateResponse {

    private String gameId;
    private GameStatus gameStatus;
    private String message;
    private Long gameLevel;
    private QuestionResponse question;
}
