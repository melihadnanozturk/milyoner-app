package org.maoco.milyoner.question.web.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuestionResponse {

    private Long questionId;
    private String questionText;
    private Long questionLevel;
    private List<AnswerResponse> answers;
}
