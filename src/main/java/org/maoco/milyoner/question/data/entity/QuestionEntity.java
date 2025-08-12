package org.maoco.milyoner.question.data.entity;

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

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "question")
    private List<AnswerEntity> answers;
}

//todo: Sorularda aktif pasif field ekleyebilirsin
