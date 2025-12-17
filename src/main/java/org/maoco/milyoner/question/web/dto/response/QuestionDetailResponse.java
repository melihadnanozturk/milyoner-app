package org.maoco.milyoner.question.web.dto.response;

import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
public class QuestionDetailResponse extends QuestionResponse {
    private List<AnswerResponse> answers;
}
