package org.maoco.milyoner.gameplay.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.maoco.milyoner.question.controller.response.AnswerResponse;

import java.util.List;

@Getter
@Setter
@Builder
public class QuestionResponse {

    private String questionText;
    private List<AnswerResponse> answers;
}
