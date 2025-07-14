package org.maoco.milyoner.question.controller.request;

import lombok.Getter;

@Getter
public class CreateAnswerQuestion {

    private String answerText;

    private Boolean isCorrect;
}
