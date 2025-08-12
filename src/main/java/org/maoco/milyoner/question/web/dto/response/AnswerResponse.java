package org.maoco.milyoner.question.web.dto.response;

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
