package org.maoco.milyoner.question.web.controller.port_in.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InsQuestionRequest {
    private Long questionId;
    private Long answerId;
}
