package org.maoco.milyoner.gameplay.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.Type;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
import org.maoco.milyoner.gameplay.service.GameStateEnum;

@Entity
@Table(name = "gamer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GamerEntity {

    //id = player_id
    @Id
    private String id;

    @Column(name = "username")
    private String username;

    @Column(name = "game_id")
    private String gameId;

    @Column(name = "question_level")
    private Long questionLevel;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "game_state_type")
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private GameStateEnum gameState;
}
