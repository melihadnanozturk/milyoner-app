package org.maoco.milyoner.question.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "questions")
@Data
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "question_text")
    private String questionText;

    @Column(name = "question_level")
    private Long questionLevel;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "answer_id",referencedColumnName = "id")
    private List<AnswerEntity> answers;
}
