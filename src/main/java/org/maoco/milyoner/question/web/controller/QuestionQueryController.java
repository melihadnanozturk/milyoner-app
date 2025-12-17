package org.maoco.milyoner.question.web.controller;

import lombok.RequiredArgsConstructor;
import org.maoco.milyoner.common.ApiResponse;
import org.maoco.milyoner.question.domain.Question;
import org.maoco.milyoner.question.service.QuestionQueryService;
import org.maoco.milyoner.question.web.dto.request.QuestionQueryRequest;
import org.maoco.milyoner.question.web.dto.response.AnswerResponse;
import org.maoco.milyoner.question.web.dto.response.QuestionDetailResponse;
import org.maoco.milyoner.question.web.dto.response.QuestionResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/panel/questions")
@RequiredArgsConstructor
public class QuestionQueryController {

    private final QuestionQueryService operationService;

    @PostMapping
    public ApiResponse<List<QuestionResponse>> getAllQuestions(@RequestBody QuestionQueryRequest request) {
        Collection<Question> questions = operationService.getAllQuestions(request);

        List<QuestionResponse> data = questions.stream()
                .map(this::mapToQuestionResponse)
                .toList();

        return ApiResponse.success(data);
    }

    @GetMapping("/{id}")
    public ApiResponse<QuestionDetailResponse> getQuestionsById(@PathVariable Long id) {
        Question question = operationService.getQuestionById(id);

        QuestionDetailResponse data = QuestionDetailResponse.builder()
                .questionId(question.getId())
                .questionText(question.getQuestionText())
                .isActivate(question.getIsActivate())
                .questionLevel(question.getQuestionLevel())
                .answers(question.getAnswers().stream().map(answer -> AnswerResponse.builder()
                        .answerId(answer.getId())
                        .isCorrect(answer.getIsCorrect())
                        .isActivate(answer.getIsActivate())
                        .answerText(answer.getAnswerText())
                        .build()).toList())
                .build();

        return ApiResponse.success(data);
    }

    private QuestionResponse mapToQuestionResponse(Question question) {
        return QuestionResponse.builder()
                .questionId(question.getId())
                .questionText(question.getQuestionText())
                .isActivate(question.getIsActivate())
                .questionLevel(question.getQuestionLevel())
                .build();
    }
}
