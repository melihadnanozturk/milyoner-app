package org.maoco.milyoner.question.data.repository;

import org.maoco.milyoner.question.data.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long>, JpaSpecificationExecutor<QuestionEntity> {

    @Query(value = "SELECT q FROM QuestionEntity q WHERE q.questionLevel = :level ORDER BY RANDOM() LIMIT 1")
    Optional<QuestionEntity> findByQuestionLevel(@Param("level") Long level);
}
