package org.maoco.milyoner.question.web.dto.request;

import lombok.Getter;

@Getter
public class CreateNewAnswerRequest extends AnswerRequest {

    private Long questionId;
}
