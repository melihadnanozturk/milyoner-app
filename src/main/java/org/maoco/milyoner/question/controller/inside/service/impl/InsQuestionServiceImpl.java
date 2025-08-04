package org.maoco.milyoner.question.controller.inside;

import lombok.RequiredArgsConstructor;
import org.maoco.milyoner.question.controller.inside.request.InsQuestionRequest;
import org.maoco.milyoner.question.controller.inside.response.InsQuestionResponse;
import org.maoco.milyoner.question.controller.response.AnswerResponse;
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
    public void setAnswer(InsQuestionRequest request) {

    }
}
