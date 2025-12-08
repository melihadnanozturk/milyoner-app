package org.maoco.milyoner.gameplay.web.dto.response;

import lombok.Builder;
import org.maoco.milyoner.gameplay.service.GameState;

@Builder
public record GameResponse(
        Long questionLevel,
        GameState gameState
) {
}
