package org.maoco.milyoner.gameplay.outside.insideApi;

import lombok.Builder;
import lombok.Data;
import org.maoco.milyoner.question.controller.response.AnswerResponse;

import java.util.List;

@Data
@Builder
public class Question {
    private Long questionId;
    private String questionText;
    private List<AnswerResponse> answers;

    public static Question of(org.maoco.milyoner.question.controller.inside.dto.response.InsQuestionResponse response) {
        return Question.builder()
                .questionId(response.getQuestionId())
                .questionText(response.getQuestionText())
                .answers(response.getAnswers())
                .build();
    }
}
