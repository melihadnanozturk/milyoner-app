package org.maoco.milyoner.gameplay.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Answer {
    private Long id;
    private Boolean isCorrect;
    private String answerText;
}
