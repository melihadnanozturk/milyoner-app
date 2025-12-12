package org.maoco.milyoner.question.data.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "answer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "answer_text")
    private String answerText;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @Column(name = "is_activate")
    private Boolean isActivate = true; //burada default değer nasıl verebilrim ?

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    QuestionEntity question;

    public AnswerEntity(String answerText, Boolean isCorrect, QuestionEntity entity) {
        this.answerText = answerText;
        this.isCorrect = isCorrect;
        this.question = entity;
        this.isActivate = true;
    }

}
