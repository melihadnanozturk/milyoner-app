package org.maoco.milyoner.question.service;

import org.maoco.milyoner.question.entity.QuestionEntity;

import java.util.Collection;

public interface QuestionQueryService {

    Collection<QuestionEntity> getAllQuestions();

    QuestionEntity getQuestionById(Long id);
}
