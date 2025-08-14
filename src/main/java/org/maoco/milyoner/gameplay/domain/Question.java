package org.maoco.milyoner.gameplay.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.maoco.milyoner.question.web.controller.port_in.dto.response.InsQuestionResponse;

import java.util.List;

@Getter
@Builder
@Setter
public class Question {
    private Long questionId;
    private String questionText;
    private List<Answer> answers;

    public static Question of(InsQuestionResponse response) {
        return Question.builder()
                .questionId(response.getQuestionId())
                .questionText(response.getQuestionText())
                .answers(response.getAnswers().stream().map(answer -> Answer.builder()
                        .id(answer.getAnswerId())
                        .answerText(answer.getAnswerText())
                        .isCorrect(answer.getIsCorrect())
                        .isActivate(answer.getIsActive())
                        .build()).toList())
                .build();
    }
}
