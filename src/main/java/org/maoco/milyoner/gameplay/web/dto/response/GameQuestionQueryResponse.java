package org.maoco.milyoner.gameplay.web.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameQuestionQueryResponse {

    private GameResponse game;
    private QuestionResponse question;
}
