package org.maoco.milyoner.gameplay.web.dto.request;

import lombok.Getter;

@Getter
public class GameQuestionAnswerRequest extends GameRequest {

    private Long questionId;
    private Long answerId;
}
