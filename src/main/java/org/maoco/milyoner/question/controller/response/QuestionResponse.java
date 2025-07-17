package org.maoco.milyoner.question.controller.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuestionResponse {

    private Long questionId;
    private String questionText;
    private List<AnswerResponse> answers;
}
