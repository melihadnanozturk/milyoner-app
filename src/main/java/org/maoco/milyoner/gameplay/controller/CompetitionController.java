package org.maoco.milyoner.gameplay.controller;

import lombok.RequiredArgsConstructor;
import org.maoco.milyoner.common.ApiResponse;
import org.maoco.milyoner.gameplay.controller.request.CompetitionAnswerRequest;
import org.maoco.milyoner.gameplay.controller.request.CompetitionQuestionQueryRequest;
import org.maoco.milyoner.gameplay.controller.response.CompetitionAnswerResponse;
import org.maoco.milyoner.gameplay.controller.response.CompetitionQueryRequestResponse;
import org.maoco.milyoner.gameplay.service.CompetitionService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/competition")
@RestController
@RequiredArgsConstructor
public class CompetitionController {

    private final CompetitionService service;

    @GetMapping("/questions")
    public ApiResponse<CompetitionQueryRequestResponse> getQuestion(@RequestBody CompetitionQuestionQueryRequest request) {
        CompetitionQueryRequestResponse data = service.getQuestion(request);

        return ApiResponse.success(data);
    }

    @PostMapping("/questions")
    public ApiResponse<CompetitionAnswerResponse> setAnswer(@RequestBody CompetitionAnswerRequest request) {
        service.setAnswer(request);
        return ApiResponse.success(null);
    }
}
