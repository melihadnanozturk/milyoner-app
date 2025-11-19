package org.maoco.milyoner.gameplay.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
public class UserScore {

    public UserScore(String username, Long score) {
        this.score = score;
        this.username = username;
    }

    //todo: simdilik questionLevel - sonrasında her sorunun puan karsiligi olacaktir
    private Long score;

    private String username;

    @Setter
    private String message;

}
