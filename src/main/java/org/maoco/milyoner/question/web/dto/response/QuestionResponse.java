package org.maoco.milyoner.question.web.dto.response;

import lombok.Data;
import lombok.experimental.SuperBuilder;


@Data
@SuperBuilder
public class QuestionResponse {

    private Long questionId;
    private String questionText;
    private Boolean isActivate;
    private Long questionLevel;
}
