package org.maoco.milyoner.question.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "answers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "answer_text")
    private String answerText;

    @Column(name = "isCorrect")
    private Boolean isCorrect;

    @Column(name = "isActivate")
    private Boolean isActivate = true; //burada default değer nasıl verebilrim ?

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    QuestionEntity question;

    public AnswerEntity(String answerText, Boolean isCorrect) {
        this.answerText = answerText;
        this.isCorrect = isCorrect;
        this.isActivate = true;
    }

}
