package org.maoco.milyoner.gameplay.controller.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnswerResponse {

    private Long id;
    private String text;
}
