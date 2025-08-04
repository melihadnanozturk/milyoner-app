package org.maoco.milyoner.question.controller.inside.service;

import org.maoco.milyoner.question.controller.inside.dto.request.InsQuestionRequest;
import org.maoco.milyoner.question.controller.inside.dto.response.InsQuestionResponse;

public interface InsQuestionService {

    InsQuestionResponse getQuestion(Long level);
    void setAnswer(InsQuestionRequest request);
}
