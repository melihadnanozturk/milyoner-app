package org.maoco.milyoner.question.web.controller;

import lombok.RequiredArgsConstructor;
import org.maoco.milyoner.common.ApiResponse;
import org.maoco.milyoner.question.domain.Answer;
import org.maoco.milyoner.question.service.AnswerQueryService;
import org.maoco.milyoner.question.web.dto.response.AnswerResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/answers")
@RequiredArgsConstructor
public class AnswerQueryController {

    private final AnswerQueryService answerQueryService;

    @GetMapping("/{id}")
    public ApiResponse<AnswerResponse> getAnswerById(@PathVariable Long id) {
        Answer answer = answerQueryService.getAnswerById(id);

        AnswerResponse data = AnswerResponse.builder()
                .answerId(answer.getId())
                .answerText(answer.getAnswerText())
                .isActivate(answer.getIsActivate())
                .isCorrect(answer.getIsCorrect())
                .build();

        return ApiResponse.success(data);
    }
}
