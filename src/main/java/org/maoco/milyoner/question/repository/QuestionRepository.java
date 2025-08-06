package org.maoco.milyoner.question.repository;

import org.maoco.milyoner.question.entity.QuestionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface QuestionRepository extends PagingAndSortingRepository<QuestionEntity, Long>, CrudRepository<QuestionEntity, Long> {

    @Query(value = "SELECT q FROM QuestionEntity q WHERE q.questionLevel = :level ORDER BY RANDOM() LIMIT 1")
    Optional<QuestionEntity> findByQuestionLevel(@Param("level") Long level);

    @Query(value = "SELECT q FROM QuestionEntity q WHERE q.id =: questionId AND q.answers")
    Optional<QuestionEntity> findByIdAndGivenAnswer(@Param("questionId") Long questionId, @Param("answerId") Long answerId);
}
