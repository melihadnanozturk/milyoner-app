package org.maoco.milyoner.gameplay.service.impl;

import org.maoco.milyoner.gameplay.controller.request.CompetitionAnswerRequest;
import org.maoco.milyoner.gameplay.controller.request.CompetitionQuestionQueryRequest;
import org.maoco.milyoner.gameplay.controller.response.CompetitionAnswerResponse;
import org.maoco.milyoner.gameplay.service.CompetitionService;
import org.maoco.milyoner.question.entity.QuestionEntity;

public class CompetitionServiceImpl implements CompetitionService {
    @Override
    public QuestionEntity getAnswer(CompetitionQuestionQueryRequest request) {
        return null;
    }

    @Override
    public CompetitionAnswerResponse setAnswer(CompetitionAnswerRequest request) {
        return null;
    }
}
