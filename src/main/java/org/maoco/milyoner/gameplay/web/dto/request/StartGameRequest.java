package org.maoco.milyoner.gameplay.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StartGameRequest {

    @NotBlank(message = "Username can not be blank")
    String username;
}
