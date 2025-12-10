package org.maoco.milyoner.question.web.controller;


import lombok.RequiredArgsConstructor;
import org.maoco.milyoner.common.ApiResponse;
import org.maoco.milyoner.question.domain.Answer;
import org.maoco.milyoner.question.service.AnswerOperationService;
import org.maoco.milyoner.question.web.dto.request.CreateNewAnswerRequest;
import org.maoco.milyoner.question.web.dto.response.AnswerResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/panel/answers/operation")
@RequiredArgsConstructor
public class AnswerOperationController {

    private final AnswerOperationService answerOperationService;

    @PostMapping
    public ApiResponse<AnswerResponse> createNewAnswer(@RequestBody CreateNewAnswerRequest request) {
        Answer newAnswer = answerOperationService.createNewAnswer(request);

        AnswerResponse response = AnswerResponse.builder()
                .answerId(newAnswer.getId())
                .answerText(newAnswer.getAnswerText())
                .isCorrect(newAnswer.getIsCorrect())
                .isActivate(newAnswer.getIsActivate())
                .build();

        return ApiResponse.success(response);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteAnswer(@PathVariable Long id) {
        String response = answerOperationService.deleteAnswer(id);

        return ApiResponse.success(response);
    }
}
