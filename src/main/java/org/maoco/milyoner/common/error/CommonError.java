package org.maoco.milyoner.common.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommonError implements Error{

    NOT_FOUND(2001,"Record not exists","Kayıt bulunamadı");

    private final Integer code;
    private final String message;
    private final String uiMessage;
}
