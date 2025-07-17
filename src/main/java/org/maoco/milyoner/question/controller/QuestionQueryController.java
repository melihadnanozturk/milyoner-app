package org.maoco.milyoner.question.controller;

import lombok.RequiredArgsConstructor;
import org.maoco.milyoner.common.ApiResponse;
import org.maoco.milyoner.question.controller.response.AnswerResponse;
import org.maoco.milyoner.question.controller.response.QuestionResponse;
import org.maoco.milyoner.question.entity.QuestionEntity;
import org.maoco.milyoner.question.service.QuestionQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionQueryController {

    private final QuestionQueryService operationService;

    @GetMapping
    public ApiResponse<List<QuestionResponse>> getAllQuestions() {
        List<QuestionEntity> entities = operationService.getAllQuestions().stream().toList();

        List<QuestionResponse> data = entities.stream()
                .map((entity) -> QuestionResponse.builder()
                        .questionId(entity.getId())
                        .questionText(entity.getQuestionText())
                        .answers(entity.getAnswers().stream()
                                .map(answer -> AnswerResponse.builder()
                                        .answerId(answer.getId())
                                        .isCorrect(answer.getIsCorrect())
                                        .isActivate(answer.getIsActivate())
                                        .answerText(answer.getAnswerText())
                                        .build())
                                .toList())
                        .build())
                .toList();

        return ApiResponse.success(data);
    }

    @GetMapping("/{id}")
    public ApiResponse<QuestionResponse> getQuestionsById(@PathVariable Long id) {
        QuestionEntity entity = operationService.getQuestionById(id);

        QuestionResponse data = QuestionResponse.builder()
                .questionId(entity.getId())
                .questionText(entity.getQuestionText())
                .answers(entity.getAnswers().stream().map(answer -> AnswerResponse.builder()
                        .answerId(answer.getId())
                        .isCorrect(answer.getIsCorrect())
                        .isActivate(answer.getIsActivate())
                        .answerText(answer.getAnswerText())
                        .build()).toList())
                .build();

        return ApiResponse.success(data);
    }
}
