package org.maoco.milyoner.question.service;

import org.maoco.milyoner.question.controller.request.CreateNewQuestionRequest;
import org.maoco.milyoner.question.controller.request.UpdateQuestionRequest;
import org.maoco.milyoner.question.entity.QuestionEntity;

public interface QuestionOperationService {

    QuestionEntity createNewQuestion(CreateNewQuestionRequest createNewQuestionRequest);

    QuestionEntity updateQuestion(UpdateQuestionRequest updateQuestionRequest);

    String deleteQuestion(Long questionId);
}
