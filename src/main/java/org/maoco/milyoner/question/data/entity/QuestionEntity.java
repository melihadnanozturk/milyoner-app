package org.maoco.milyoner.question.data.entity;

import jakarta.persistence.*;
import lombok.*;
import org.maoco.milyoner.question.domain.Question;

import java.util.List;

@Entity
@Table(name = "question")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question_text")
    private String questionText;

    @Column(name = "question_level")
    private Long questionLevel;

    @Column(name = "is_activate")
    private Boolean isActivate;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "question",
            orphanRemoval = true)
    private List<AnswerEntity> answers;

    public void removeAnswer(AnswerEntity answer) {
        if (answer == null) {
            return;
        }
        if (answers != null) {
            answers.remove(answer);
        }
        answer.setQuestion(null);
    }

    public static QuestionEntity of(Question question) {
        return QuestionEntity.builder()
                .id(question.getId())
                .questionText(question.getQuestionText())
                .questionLevel(question.getQuestionLevel())
                .build();
    }
}

//todo: Sorularda aktif pasif field ekleyebilirsin
