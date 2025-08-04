package org.maoco.milyoner.question.controller.request;

import jakarta.validation.constraints.*;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateNewQuestionRequest {

    @NotBlank(message = "Question Text cannot be blank")
    private String questionText;

    @Size(min = 4, max = 10, message = "Answer size must be between 4 and 10")
    private List<CreateAnswerQuestion> answers;

    @NotNull(message = "Question Level cannot be blank")
    @Min(value = 1, message = "Question Level must be greater than 0")
    @Max(value = 10, message = "Question Level must be smaller than 10")
    private Long questionLevel;
}
