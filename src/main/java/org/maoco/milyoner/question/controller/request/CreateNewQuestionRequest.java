package org.maoco.milyoner.question.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateNewQuestionRequest {

    @NotBlank(message = "Question text cannot be blank")
    private String questionText;

    @Size(min = 4, max = 10, message = "Answer size must be between 4 and 10")
    private List<CreateAnswerQuestion> answers;
}
