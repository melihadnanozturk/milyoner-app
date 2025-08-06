package org.maoco.milyoner.gameplay.controller.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameQuestionQueryResponse {

    private GameResponse game;
    private QuestionResponse question;
}
