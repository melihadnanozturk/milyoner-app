package org.maoco.milyoner.question.web.dto.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
public class QuestionDetailResponse extends QuestionResponse {
    private List<AnswerResponse> answers;
}
