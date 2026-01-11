package org.maoco.milyoner.question.web.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAnswerRequest extends AnswerRequest {

    @JsonIgnore
    private Long answerId;
}
