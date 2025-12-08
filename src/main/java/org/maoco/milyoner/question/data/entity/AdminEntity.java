package org.maoco.milyoner.question.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

@Table(name = "admin_user")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminEntity {

    @Id
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "pass")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "game_state_type")
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private UserRoles userRoles;
}

