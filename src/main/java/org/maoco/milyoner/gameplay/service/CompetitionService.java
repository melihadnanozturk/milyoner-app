package org.maoco.milyoner.gameplay.service;

import org.maoco.milyoner.gameplay.controller.request.CompetitionAnswerRequest;
import org.maoco.milyoner.gameplay.controller.request.CompetitionQuestionQueryRequest;
import org.maoco.milyoner.gameplay.controller.response.CompetitionAnswerResponse;
import org.maoco.milyoner.gameplay.controller.response.CompetitionQueryRequestResponse;

public interface CompetitionService {
    CompetitionQueryRequestResponse getQuestion(CompetitionQuestionQueryRequest request);

    CompetitionAnswerResponse setAnswer(CompetitionAnswerRequest request);
}
