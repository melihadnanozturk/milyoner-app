package org.maoco.milyoner.question.service.exception;

import org.maoco.milyoner.common.exception.MilyonerException;
import org.maoco.milyoner.question.service.error.AnswerError;

public class CreateAnswerException extends MilyonerException {

    public CreateAnswerException() {
        super(AnswerError.MULTIPLE_TRUTH_ERROR);
    }
}
