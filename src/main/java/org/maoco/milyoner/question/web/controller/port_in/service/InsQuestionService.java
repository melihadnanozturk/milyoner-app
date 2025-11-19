package org.maoco.milyoner.question.web.controller.port_in.service;

import org.maoco.milyoner.gameplay.domain.Game;
import org.maoco.milyoner.gameplay.service.GameStateEnum;
import org.maoco.milyoner.gameplay.web.dto.request.GameQuestionAnswerRequest;
import org.maoco.milyoner.question.data.entity.AnswerEntity;
import org.maoco.milyoner.question.domain.Question;
import org.maoco.milyoner.question.service.QuestionQueryService;
import org.maoco.milyoner.question.web.controller.port_in.dto.response.InsAnswerResponse;
import org.maoco.milyoner.question.web.controller.port_in.dto.response.InsQuestionResponse;
import org.springframework.stereotype.Service;

@Service
public class InsQuestionService {

    private final QuestionQueryService queryService;

    public InsQuestionService(QuestionQueryService questionQueryService) {
        this.queryService = questionQueryService;
    }

    public InsQuestionResponse getQuestion(Long level) {
        Question question = queryService.getQuestionByLevel(level);

        //todo: burada tum cevaplar verilmeyecek, 1 doğru cevap ve 3 tane random yanlış cevap verilecek
        //todo: muhtemelen db'ye 2 farklı sorgu atılabilir
        return InsQuestionResponse.builder()
                .questionId(question.getId())
                .questionText(question.getQuestionText())
                .answers(question.getAnswers().stream().map(answer -> InsAnswerResponse.builder()
                        .answerId(answer.getId())
                        .answerText(answer.getAnswerText())
                        .isCorrect(answer.getIsCorrect())
                        .isActive(answer.getIsActivate())
                        .build()).toList())
                .build();
    }

    public InsAnswerResponse handleAnswer(Long answerId, Long questionId) {
        AnswerEntity answerEntity = queryService.handleAnswer(questionId, answerId);
        return InsAnswerResponse.builder()
                .isCorrect(answerEntity.getIsCorrect())
                .answerId(answerEntity.getId())
                .answerText(answerEntity.getAnswerText())
                .build();
    }

    public Game checkAnswer(GameQuestionAnswerRequest request) {
        Game game = Game.buildGameFromRequest(request);
        var data = handleAnswer(request.getAnswerId(), request.getQuestionId());


        if (!data.getIsCorrect().equals(true)) {
            game.updateGameState(GameStateEnum.LOST);
            return game;
        }

        if (game.getQuestionLevel() == 10L) {
            game.updateGameState(GameStateEnum.WON);
            return game;
        }

        game.setQuestionLevel(game.getQuestionLevel() + 1);

        return game;
    }
}
