package org.maoco.milyoner.question.repository;

import org.maoco.milyoner.question.entity.QuestionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface QuestionRepository extends PagingAndSortingRepository<QuestionEntity, Long>, CrudRepository<QuestionEntity,Long> {
}
