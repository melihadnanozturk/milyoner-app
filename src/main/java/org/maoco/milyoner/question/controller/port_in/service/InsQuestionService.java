package org.maoco.milyoner.question.controller.port_in.service;

import org.maoco.milyoner.question.controller.port_in.dto.response.InsAnswerResponse;
import org.maoco.milyoner.question.controller.port_in.dto.response.InsQuestionResponse;
import org.maoco.milyoner.question.entity.AnswerEntity;
import org.maoco.milyoner.question.entity.QuestionEntity;
import org.maoco.milyoner.question.service.QuestionQueryService;
import org.springframework.stereotype.Service;

@Service
public class InsQuestionService {

    private final QuestionQueryService queryService;

    public InsQuestionService(QuestionQueryService questionQueryService) {
        this.queryService = questionQueryService;
    }

    public InsQuestionResponse getQuestion(Long level) {
        QuestionEntity entity = queryService.getQuestionByLevel(level);

        //todo: burada tum cevaplar verilmeyecek, 1 doğru cevap ve 3 tane random yanlış cevap verilecek
        //todo: muhtemelen db'ye 2 farklı sorgu atılabilir
        return InsQuestionResponse.builder()
                .questionId(entity.getId())
                .questionText(entity.getQuestionText())
                .answers(entity.getAnswers().stream().map(answer -> InsAnswerResponse.builder()
                        .answerId(answer.getId())
                        .answerText(answer.getAnswerText())
                        .build()).toList())
                .build();
    }

    public InsAnswerResponse handleAnswer(Long answerId, Long questionId) {
        AnswerEntity answerEntity = queryService.handleAnswer(answerId, questionId);
        return InsAnswerResponse.builder()
                .isCorrect(answerEntity.getIsCorrect())
                .answerId(answerEntity.getId())
                .answerText(answerEntity.getAnswerText())
                .build();
    }
}
