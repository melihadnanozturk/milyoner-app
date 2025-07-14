package org.maoco.milyoner.question.controller.request;

import lombok.Getter;

import java.util.List;

@Getter
public class CreateNewQuestionRequest {

    private String questionText;

    private List<CreateAnswerQuestion> answers;
}
