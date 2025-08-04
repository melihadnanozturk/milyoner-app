package org.maoco.milyoner.question.controller.inside;

import org.maoco.milyoner.question.controller.inside.request.InsQuestionRequest;
import org.maoco.milyoner.question.controller.inside.response.InsQuestionResponse;

public interface InsQuestionService {

    InsQuestionResponse getQuestion(Long level);
    void setAnswer(InsQuestionRequest request);
}
