package org.maoco.milyoner.common.exception;

import lombok.Getter;
import org.maoco.milyoner.common.error.CommonError;

@Getter
public class NotFoundException extends MilyonerException{

    public NotFoundException(){
        super(CommonError.NOT_FOUND);
    }

    public NotFoundException(String message){
        super(CommonError.NOT_FOUND,message);
    }

}
