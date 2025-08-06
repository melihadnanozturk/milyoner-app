package org.maoco.milyoner.question.controller.inside.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InsAnswerResponse {

    private Long answerId;
    private String answerText;
    private Boolean isCorrect;
}
