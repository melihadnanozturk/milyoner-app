package org.maoco.milyoner.gameplay.controller.response;

import lombok.Builder;
import lombok.Data;
import org.maoco.milyoner.question.controller.response.AnswerResponse;

import java.util.List;

@Data
@Builder
public class CompetitionQueryRequestResponse {
    private Long questionId;
    private String questionText;
    private List<AnswerResponse> answers;

    public static CompetitionQueryRequestResponse of(org.maoco.milyoner.question.controller.inside.dto.response.InsQuestionResponse response) {
        return CompetitionQueryRequestResponse.builder()
                .questionId(response.getQuestionId())
                .questionText(response.getQuestionText())
                .answers(response.getAnswers())
                .build();
    }
}
