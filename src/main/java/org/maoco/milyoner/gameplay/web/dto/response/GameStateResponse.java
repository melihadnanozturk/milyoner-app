package org.maoco.milyoner.gameplay.web.dto.response;


import lombok.Getter;
import org.maoco.milyoner.gameplay.domain.GamePhase;

@Getter
public class GameStateResponse {

    private String gameId;
    private GamePhase gamePhase;
    private String message;
    private Long gameLevel;
    private QuestionResponse question;
}
