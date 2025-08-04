package org.maoco.milyoner.question.service.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.maoco.milyoner.common.error.Error;

@Getter
@RequiredArgsConstructor
public enum AnswerError implements Error {

    MULTIPLE_TRUTH_ERROR(2000,"There can be only one true answer","Bir sorunun birden fazla doğru yanıtı olamaz");

    private final Integer code;
    private final String message;
    private final String uiMessage;
}
