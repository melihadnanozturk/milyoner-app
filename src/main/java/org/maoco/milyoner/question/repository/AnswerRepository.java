package org.maoco.milyoner.question.repository;

import org.maoco.milyoner.question.entity.AnswerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface AnswerRepository extends PagingAndSortingRepository<AnswerEntity, Long>, CrudRepository<AnswerEntity, Long> {


    //@Query("SELECT a FROM AnswerEntity a WHERE a.id =:answerId AND a.question.id =:questionId")
    Optional<AnswerEntity> findByIdAndQuestionIdAndIsActivate(Long id, Long questionId, Boolean isActivate);
}
