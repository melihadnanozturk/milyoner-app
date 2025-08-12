package org.maoco.milyoner.question.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.maoco.milyoner.question.data.entity.QuestionEntity;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class Question {

    private Long id;
    private String questionText;
    private Long questionLevel;
    private List<Answer> answers;

    public static Question of(QuestionEntity entity) {
        return Question.builder()
                .id(entity.getId())
                .questionText(entity.getQuestionText())
                .questionLevel(entity.getQuestionLevel())
                .answers(entity.getAnswers().stream()
                        .map(answer -> Answer.builder()
                                .id(answer.getId())
                                .answerText(answer.getAnswerText())
                                .isCorrect(answer.getIsCorrect())
                                .isActivate(answer.getIsActivate())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
