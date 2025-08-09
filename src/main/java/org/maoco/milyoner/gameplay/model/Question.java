package org.maoco.milyoner.gameplay.model;

import lombok.Builder;
import lombok.Getter;
import org.maoco.milyoner.gameplay.controller.response.AnswerResponse;
import org.maoco.milyoner.question.controller.port_in.dto.response.InsQuestionResponse;

import java.util.List;

@Getter
@Builder
public class Question {
    private Long questionId;
    private String questionText;
    private List<AnswerResponse> answers;

    public static Question of(InsQuestionResponse response) {
        return Question.builder()
                .questionId(response.getQuestionId())
                .questionText(response.getQuestionText())
                .answers(response.getAnswers().stream().map(answer-> AnswerResponse.builder()
                        .id(answer.getAnswerId())
                        .text(answer.getAnswerText())
                        .build()).toList())
                .build();
    }
}
