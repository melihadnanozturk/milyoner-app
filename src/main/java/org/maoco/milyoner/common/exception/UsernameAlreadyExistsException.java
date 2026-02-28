package org.maoco.milyoner.common.exception;

import org.maoco.milyoner.common.error.AuthError;

public class UsernameAlreadyExistsException extends MilyonerException {

    public UsernameAlreadyExistsException() {
        super(AuthError.USERNAME_ALREADY_EXISTS);
    }

    public UsernameAlreadyExistsException(String message) {
        super(AuthError.USERNAME_ALREADY_EXISTS, message);
    }
}
