package org.maoco.milyoner.question.web.controller.port_in.service;

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
