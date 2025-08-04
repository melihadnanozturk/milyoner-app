package org.maoco.milyoner.common.exception;

import lombok.Getter;
import org.maoco.milyoner.common.error.Error;

@Getter
public class MilyonerException extends RuntimeException{

    private Error error;
    private Integer code;
    private String message;
    private String uiMessage;

    public MilyonerException(Error error){
        this.error = error;
        this.code = error.getCode();
        this.message = error.getMessage();
        this.uiMessage = error.getUiMessage();
    }

    public MilyonerException(Error error,String message){
        this.error = error;
        this.code = error.getCode();
        this.message = message;
        this.uiMessage = error.getUiMessage();
    }

}
