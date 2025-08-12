package org.maoco.milyoner.question.web.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateQuestionResponse {

    private Long questionId;
    private String questionText;
    private Long questionLevel;
}
