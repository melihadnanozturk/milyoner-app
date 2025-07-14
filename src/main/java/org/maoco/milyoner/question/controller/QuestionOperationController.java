package org.maoco.milyoner.question.controller;

import lombok.RequiredArgsConstructor;
import org.maoco.milyoner.question.controller.request.CreateNewQuestionRequest;
import org.maoco.milyoner.question.controller.request.UpdateQuestionRequest;
import org.maoco.milyoner.question.controller.response.CreateNewQuestionResponse;
import org.maoco.milyoner.question.controller.response.UpdateQuestionResponse;
import org.maoco.milyoner.question.entity.QuestionEntity;
import org.maoco.milyoner.question.service.QuestionOperationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionOperationController {

    private final QuestionOperationService operationService;

    @PostMapping
    public CreateNewQuestionResponse createNewQuestion(@RequestBody CreateNewQuestionRequest request) {
        QuestionEntity response = operationService.createNewQuestion(request);

        return response;
    }

    @PutMapping
    public UpdateQuestionResponse updateQuestion(@RequestBody UpdateQuestionRequest updateQuestionRequest) {
        QuestionEntity response =  operationService.updateQuestion(updateQuestionRequest);

        return response ;
    }

    @DeleteMapping("/{questionId}")
    public String deleteQuestion(@PathVariable Long questionId) {
        String response = operationService.deleteQuestion(questionId);

        return response;
    }

}
