package org.maoco.milyoner.question.service.spec;

import org.maoco.milyoner.question.data.entity.QuestionEntity;
import org.springframework.data.jpa.domain.Specification;

public class QuestionSpecification {

    public static Specification<QuestionEntity> getQuestionsByQuestionLevel(Long questionLevel) {
        return (root, criteriaQuery, criteriaBuilder) ->
                questionLevel == null
                        ? null
                        : criteriaBuilder.equal(root.get("questionLevel"), questionLevel);
    }
}
