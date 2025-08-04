package org.maoco.milyoner.common.error;

public interface Error {

    Integer getCode();

    String getMessage();

    String getUiMessage();
}
