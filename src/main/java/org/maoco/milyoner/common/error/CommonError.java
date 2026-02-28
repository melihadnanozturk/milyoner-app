package org.maoco.milyoner.common.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommonError implements Error {

    NOT_FOUND(2001, "Record not found", "Kayıt bulunamadı"),
    INVALID_SECURITY_CONTEXT(2002, "Invalid security context", "Güvenlik bağlamı geçersiz"),
    INTERNAL_SERVER_ERROR(2003, "Internal server error", "Sunucu hatası oluştu"),
    DATABASE_ERROR(2004, "Database operation failed", "Veritabanı işlemi başarısız");

    private final Integer code;
    private final String message;
    private final String uiMessage;
}
