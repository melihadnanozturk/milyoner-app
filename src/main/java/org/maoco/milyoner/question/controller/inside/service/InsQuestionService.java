package org.maoco.milyoner.question.controller.inside.service;

import org.maoco.milyoner.question.controller.inside.dto.response.InsAnswerResponse;
import org.maoco.milyoner.question.controller.inside.dto.response.InsQuestionResponse;

public interface InsQuestionService {

    InsQuestionResponse getQuestion(Long level);

    //TODO : microservice olduğu zaman burasını requestClass yapmamız gerekebilir
    InsAnswerResponse handleAnswer(Long answerId, Long questionId);
}
