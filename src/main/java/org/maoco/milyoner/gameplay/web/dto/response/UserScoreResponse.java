package org.maoco.milyoner.gameplay.web.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserScoreResponse {
    private Long score;
    private String username;
    private String message;
}
