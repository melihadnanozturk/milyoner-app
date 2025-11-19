package org.maoco.milyoner.question.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.maoco.milyoner.question.domain.Question;

import java.util.List;

@Entity
@Table(name = "question")
@Data
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
    private List<AnswerEntity> answers;

    public static QuestionEntity of(Question question) {
        return QuestionEntity.builder()
                .id(question.getId())
                .questionText(question.getQuestionText())
                .questionLevel(question.getQuestionLevel())
                .build();
    }
}

//todo: Sorularda aktif pasif field ekleyebilirsin
