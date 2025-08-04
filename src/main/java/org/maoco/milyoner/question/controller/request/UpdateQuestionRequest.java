package org.maoco.milyoner.question.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateQuestionRequest {

    @JsonIgnore
    private Long questionId;

    @NotBlank(message = "Question Text cannot be blank")
    private String questionText;

    @NotNull(message = "Question Level cannot be blank")
    @Max(value = 10, message = "Question Level must be smaller than 10")
    @Min(value = 1, message = "Question Level must be greater than 10")
    private Long questionLevel;
}
