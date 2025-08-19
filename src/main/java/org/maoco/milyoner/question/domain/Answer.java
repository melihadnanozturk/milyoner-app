package org.maoco.milyoner.question.domain;

import lombok.Builder;
import lombok.Getter;
import org.maoco.milyoner.question.data.entity.AnswerEntity;

@Builder
@Getter
public class Answer {

    private Long id;
    private String answerText;
    private Boolean isCorrect; // ??
    private Boolean isActivate; // ??

    public static Answer of(AnswerEntity saved) {
        return Answer.builder()
                .id(saved.getId())
                .answerText(saved.getAnswerText())
                .isCorrect(saved.getIsCorrect())
                .isActivate(saved.getIsActivate())
                .build();
    }

    // Additional methods or logic can be added here if needed
}
