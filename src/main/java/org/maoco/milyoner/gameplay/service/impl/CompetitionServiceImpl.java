package org.maoco.milyoner.gameplay.service.impl;

import lombok.RequiredArgsConstructor;
import org.maoco.milyoner.gameplay.controller.request.CompetitionAnswerRequest;
import org.maoco.milyoner.gameplay.controller.request.CompetitionQuestionQueryRequest;
import org.maoco.milyoner.gameplay.controller.response.CompetitionAnswerResponse;
import org.maoco.milyoner.gameplay.controller.response.CompetitionQueryRequestResponse;
import org.maoco.milyoner.gameplay.service.CompetitionService;
import org.maoco.milyoner.question.controller.inside.service.InsQuestionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompetitionServiceImpl implements CompetitionService {

    private final InsQuestionService questionService;

    //TODO : Burada paket'e özel olan bir domaine çevirip, buradan api ye cevap dönmesi gerekir.
    //TODO : Level set edilir mi ?.
    @Override
    public CompetitionQueryRequestResponse getQuestion(CompetitionQuestionQueryRequest request) {
        var data = questionService.getQuestion(request.getLevel());
        return CompetitionQueryRequestResponse.of(data);
    }

    @Override
    public CompetitionAnswerResponse setAnswer(CompetitionAnswerRequest request) {
        return null;
    }
}
