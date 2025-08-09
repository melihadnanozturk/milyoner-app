package org.maoco.milyoner.question.controller.port_in.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InsQuestionRequest {
    private Long questionId;
    private Long answerId;
}
