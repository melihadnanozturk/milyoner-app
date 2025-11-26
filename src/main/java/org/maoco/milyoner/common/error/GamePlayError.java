package org.maoco.milyoner.common.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GamePlayError implements Error {

    WRONG_ANSWER(5001, "Answer is incorrect", "Verilen cevap yanlıştı"),
    INCORRECT_STATUS(6001, "Player Status is incorrect", "Oyuncu statusu yanlıştır");

    private final Integer code;
    private final String message;
    private final String uiMessage;
}
