package org.maoco.milyoner.question.web.controller;

import lombok.RequiredArgsConstructor;
import org.maoco.milyoner.common.ApiResponse;
import org.maoco.milyoner.question.data.entity.QuestionEntity;
import org.maoco.milyoner.question.domain.Question;
import org.maoco.milyoner.question.service.QuestionQueryService;
import org.maoco.milyoner.question.web.dto.request.QuestionQueryRequest;
import org.maoco.milyoner.question.web.dto.response.AnswerResponse;
import org.maoco.milyoner.question.web.dto.response.QuestionResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionQueryController {

    private final QuestionQueryService operationService;

    @GetMapping
    public ApiResponse<List<QuestionResponse>> getAllQuestions(QuestionQueryRequest request) {
        Collection<Question> questions = operationService.getAllQuestions(request);

        List<QuestionResponse> data = questions.stream()
                .map(entity -> QuestionResponse.builder()
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
        Question question = operationService.getQuestionById(id);

        QuestionResponse data = QuestionResponse.builder()
                .questionId(question.getId())
                .questionText(question.getQuestionText())
                .answers(question.getAnswers().stream().map(answer -> AnswerResponse.builder()
                        .answerId(answer.getId())
                        .isCorrect(answer.getIsCorrect())
                        .isActivate(answer.getIsActivate())
                        .answerText(answer.getAnswerText())
                        .build()).toList())
                .build();

        return ApiResponse.success(data);
    }
}
