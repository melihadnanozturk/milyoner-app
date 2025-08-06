package org.maoco.milyoner.question.controller.inside.service.impl;

import lombok.RequiredArgsConstructor;
import org.maoco.milyoner.question.controller.inside.dto.response.InsAnswerResponse;
import org.maoco.milyoner.question.controller.inside.service.InsQuestionService;
import org.maoco.milyoner.question.controller.inside.dto.response.InsQuestionResponse;
import org.maoco.milyoner.question.controller.response.AnswerResponse;
import org.maoco.milyoner.question.entity.AnswerEntity;
import org.maoco.milyoner.question.entity.QuestionEntity;
import org.maoco.milyoner.question.service.QuestionQueryService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsQuestionServiceImpl implements InsQuestionService {

    private final QuestionQueryService queryService;

    @Override
    public InsQuestionResponse getQuestion(Long level) {
        QuestionEntity entity = queryService.getQuestionByLevel(level);

        //todo: burada tum cevaplar verilmeyecek, 1 doğru cevap ve 3 tane random yanlış cevap verilecek
        //todo: muhtemelen db'ye 2 farklı sorgu atılabilir
        return InsQuestionResponse.builder()
                .questionId(entity.getId())
                .questionText(entity.getQuestionText())
                .answers(entity.getAnswers().stream().map(answer -> AnswerResponse.builder()
                        .answerId(answer.getId())
                        .isCorrect(answer.getIsCorrect())
                        .isActivate(answer.getIsActivate())
                        .answerText(answer.getAnswerText())
                        .build()).toList())
                .build();
    }

    @Override
    public InsAnswerResponse handleAnswer(Long answerId, Long questionId) {
        AnswerEntity answerEntity = queryService.handleAnswer(answerId, questionId);
        return InsAnswerResponse.builder()
                .answerId(answerEntity.getId())
                .answerText(answerEntity.getAnswerText())
                .isCorrect(answerEntity.getIsCorrect())
                .build();
    }
}
