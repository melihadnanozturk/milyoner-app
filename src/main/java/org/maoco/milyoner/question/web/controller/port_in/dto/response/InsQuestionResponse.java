package org.maoco.milyoner.question.web.controller.port_in.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class InsQuestionResponse {
    private Long questionId;
    private String questionText;
    private List<InsAnswerResponse> answers;
}
