package org.maoco.milyoner.question.web.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.maoco.milyoner.common.ApiResponse;
import org.maoco.milyoner.question.domain.Answer;
import org.maoco.milyoner.question.service.AnswerOperationService;
import org.maoco.milyoner.question.web.dto.request.CreateNewAnswerRequest;
import org.maoco.milyoner.question.web.dto.request.UpdateAnswerRequest;
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

    @PutMapping("/{id}")
    public ApiResponse<AnswerResponse> updateAnswer(@PathVariable Long id, @Valid @RequestBody UpdateAnswerRequest request) {
        request.setAnswerId(id);
        Answer updatedAnswer = answerOperationService.updateAnswer(request);

        AnswerResponse response = AnswerResponse.builder()
                .answerId(updatedAnswer.getId())
                .answerText(updatedAnswer.getAnswerText())
                .isCorrect(updatedAnswer.getIsCorrect())
                .isActivate(updatedAnswer.getIsActivate())
                .build();

        return ApiResponse.success(response);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteAnswer(@PathVariable Long id) {
        String response = answerOperationService.deleteAnswer(id);

        return ApiResponse.success(response);
    }
}
