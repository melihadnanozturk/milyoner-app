package org.maoco.milyoner.gameplay.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Answer {
    private Long id;
    private String answerText;
    private Boolean isCorrect;
    private Boolean isActivate; // ??
}
