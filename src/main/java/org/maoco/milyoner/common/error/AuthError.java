package org.maoco.milyoner.common.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthError implements Error {

    USER_NOT_FOUND(2001, "User not found", "Kullanıcı bulunamadı");

    private final Integer code;
    private final String message;
    private final String uiMessage;
}
