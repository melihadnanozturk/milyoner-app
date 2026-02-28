package org.maoco.milyoner.common.exception;

import org.maoco.milyoner.common.error.CommonError;

public class InvalidSecurityContextException extends MilyonerException {

    public InvalidSecurityContextException() {
        super(CommonError.INVALID_SECURITY_CONTEXT);
    }

    public InvalidSecurityContextException(String message) {
        super(CommonError.INVALID_SECURITY_CONTEXT, message);
    }
}
