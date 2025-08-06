package org.maoco.milyoner.common.exception;

import org.maoco.milyoner.common.error.Error;
import org.maoco.milyoner.common.error.GamePlayError;

public class WrongAnswerException extends MilyonerException {

    public WrongAnswerException() {
        super(GamePlayError.WRONG_ANSWER,"Answer is false");
    }
}
