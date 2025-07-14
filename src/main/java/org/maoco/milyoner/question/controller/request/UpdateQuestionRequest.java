package org.maoco.milyoner.question.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

@Getter
public class UpdateQuestionRequest {

    @JsonIgnore
    private Long questionId;

    private String questionText;
}
