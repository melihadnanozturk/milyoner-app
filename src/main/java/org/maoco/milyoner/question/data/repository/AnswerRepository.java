package org.maoco.milyoner.question.data.repository;

import org.maoco.milyoner.question.data.entity.AnswerEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AnswerRepository extends PagingAndSortingRepository<AnswerEntity, Long>, CrudRepository<AnswerEntity, Long> {


    @Query("SELECT a FROM AnswerEntity a WHERE a.id =:answerId AND a.question.id =:questionId AND a.isActivate = :isActivate")
    Optional<AnswerEntity> findByIdAndQuestionIdAndIsActivate(@Param("answerId") Long answerId, @Param("questionId") Long questionId, Boolean isActivate);

    Long countByQuestionIdAndIsActivate(Long questionId, Boolean isActivate);
}
