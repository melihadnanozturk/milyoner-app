package org.maoco.milyoner.question.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.maoco.milyoner.common.ApiResponse;
import org.maoco.milyoner.question.domain.Question;
import org.maoco.milyoner.question.service.QuestionOperationService;
import org.maoco.milyoner.question.web.dto.request.CreateNewQuestionRequest;
import org.maoco.milyoner.question.web.dto.request.UpdateQuestionRequest;
import org.maoco.milyoner.question.web.dto.response.AnswerResponse;
import org.maoco.milyoner.question.web.dto.response.QuestionDetailResponse;
import org.maoco.milyoner.question.web.dto.response.QuestionResponse;
import org.maoco.milyoner.question.web.dto.response.UpdateQuestionResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/panel/questions/operation")
@RequiredArgsConstructor
public class QuestionOperationController {

    private final QuestionOperationService operationService;

    @PostMapping
    public ApiResponse<QuestionResponse> createNewQuestion(@RequestBody @Valid CreateNewQuestionRequest request) {
        Question question = operationService.createNewQuestion(request);

        QuestionResponse data = QuestionDetailResponse.builder()
                .questionId(question.getId())
                .questionText(question.getQuestionText())
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

    @PutMapping("/{questionId}")
    public ApiResponse<UpdateQuestionResponse> updateQuestion(@PathVariable Long questionId, @Valid @RequestBody UpdateQuestionRequest request) {
        request.setQuestionId(questionId);
        Question question = operationService.updateQuestion(request);

        UpdateQuestionResponse data = UpdateQuestionResponse.builder()
                .questionId(question.getId())
                .questionText(question.getQuestionText())
                .questionLevel(question.getQuestionLevel())
                .isActivate(question.getIsActivate())
                .build();

        return ApiResponse.success(data);
    }

    @DeleteMapping("/{questionId}")
    public ApiResponse<String> deleteQuestion(@PathVariable Long questionId) {
        String response = operationService.deleteQuestion(questionId);

        return ApiResponse.success(response);
    }

}
