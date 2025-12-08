package org.maoco.milyoner.question.data.entity;

import lombok.Getter;

public enum UserRoles {
    
    ADMIN("Admin"),
    READER("Reader");
    
    @Getter
    private final String value;
    
    UserRoles(String value) {
        this.value = value;
    }
}
