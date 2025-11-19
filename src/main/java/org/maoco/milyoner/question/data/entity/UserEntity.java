package org.maoco.milyoner.question.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.maoco.milyoner.gameplay.service.GameStateEnum;

@Entity
@Table(name = "user")
@Data
public class UserEntity {

    //id = player_id
    @Id
    private String id;

    @Column(name = "username")
    private String username;

    @Column(name = "game_id")
    private String gameId;

    @Column(name = "question_level")
    private Long questionLevel;

    @Column(name = "game_state")
    private GameStateEnum gameState;
}
