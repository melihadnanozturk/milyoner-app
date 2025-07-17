package org.maoco.milyoner.question.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateQuestionRequest {

    @JsonIgnore
    private Long questionId;

    @NotBlank(message = "Question text cannot be blank")
    private String questionText;
}
