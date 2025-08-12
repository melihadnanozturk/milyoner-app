package org.maoco.milyoner.question.web.controller.port_in.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InsAnswerResponse {

    private Long answerId;
    private String answerText;
    private Boolean isCorrect;
}
