package org.maoco.milyoner.question.controller.inside.response;

import lombok.Builder;
import lombok.Data;
import org.maoco.milyoner.question.controller.response.AnswerResponse;

import java.util.List;

@Data
@Builder
public class InsQuestionResponse {
    private Long questionId;
    private String questionText;
    private List<AnswerResponse> answers;
}
