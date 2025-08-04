package org.maoco.milyoner.gameplay.outside.insideApi;

import lombok.Builder;
import lombok.Data;
import org.maoco.milyoner.question.controller.response.AnswerResponse;
import org.maoco.milyoner.question.entity.QuestionEntity;

import java.util.List;

@Data
@Builder
public class InsQuestionResponse {
    private Long questionId;
    private String questionText;
    private List<AnswerResponse> answers;

    public static InsQuestionResponse of(org.maoco.milyoner.question.controller.inside.dto.response.InsQuestionResponse response) {
        return InsQuestionResponse.builder()
                .questionId(response.getQuestionId())
                .questionText(response.getQuestionText())
                .answers(response.getAnswers())
                .build();
    }
}
