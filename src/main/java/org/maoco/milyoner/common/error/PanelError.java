package org.maoco.milyoner.common.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PanelError implements Error {

    ACTIVE_ANSWER_NUMBER_ERROR(5001, "Each question must have at least 4 active answers. Please check the answer count.", "Her sorunun minimum 4 tane cevabı olmak zorundadır. Lütfen cevap sayıları kontrol edin"),
    CORRECT_ANSWER_NUMBER_ERROR(5002, "Each question must have exactly one correct answer. Please check the answers.", "Her sorunun sadece bir tane doğru yanıtı olmak zorundadır. Lütfen cevapları kontrol edin"),
    WRONG_ANSWER_NUMBER_ERROR(5003, "Each question must have at least 3 wrong answers. Please check the answers.", "Her sorunun en az 3 tane yanlış yanıtı olmak zorundadır. Lütfen cevapları kontrol edin");

    private final Integer code;
    private final String message;
    private final String uiMessage;
}
