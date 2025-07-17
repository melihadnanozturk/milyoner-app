package org.maoco.milyoner.question.controller.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnswerResponse {

    private Long answerId;
    private Boolean isCorrect;
    private Boolean isActivate;
    private String answerText;
}
