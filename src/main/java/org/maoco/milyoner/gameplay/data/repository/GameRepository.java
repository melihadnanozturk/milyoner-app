package org.maoco.milyoner.gameplay.data.repository;

import org.maoco.milyoner.gameplay.data.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, String> {
    Optional<GameEntity> findByUsername(String username);

    Optional<GameEntity> findByUsernameAndId(String username, String id);
}
