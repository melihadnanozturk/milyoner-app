package org.maoco.milyoner.gameplay.model;

import lombok.Builder;
import lombok.Getter;
import org.maoco.milyoner.question.controller.inside.dto.response.InsQuestionResponse;
import org.maoco.milyoner.question.controller.response.AnswerResponse;

import java.util.List;

@Getter
@Builder
public class Question {
    private String questionText;
    private List<AnswerResponse> answers;

    public static Question of(InsQuestionResponse response) {
        return Question.builder()
                .questionText(response.getQuestionText())
                .answers(response.getAnswers())
                .build();
    }
}
