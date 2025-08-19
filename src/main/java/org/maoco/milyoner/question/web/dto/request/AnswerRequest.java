package org.maoco.milyoner.question.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AnswerRequest {
    @NotBlank(message = "Answer Text cannot be blank")
    private String answerText;

    @NotNull(message = "Answer Level cannot be blank")
    private Boolean isCorrect;

    @NotNull(message = "Answer Level cannot be blank")
    private Boolean isActive;
}
