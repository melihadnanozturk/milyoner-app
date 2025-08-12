package org.maoco.milyoner.question.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Answer {

    private Long id;
    private String answerText;
    private Boolean isCorrect; // ??
    private Boolean isActivate; // ??

    // Additional methods or logic can be added here if needed
}
