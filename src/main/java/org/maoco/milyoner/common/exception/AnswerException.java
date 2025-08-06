package org.maoco.milyoner.common.exception;

import org.maoco.milyoner.common.error.CommonError;

public class AnswerException extends MilyonerException {
    public AnswerException(String message) {
        super(CommonError.NOT_FOUND, message);
    }
}
