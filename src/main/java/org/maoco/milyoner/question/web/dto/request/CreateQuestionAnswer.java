package org.maoco.milyoner.question.web.dto.request;

import lombok.Getter;

@Getter
public class CreateQuestionAnswer {

    private String answerText;

    private Boolean isCorrect;
}
