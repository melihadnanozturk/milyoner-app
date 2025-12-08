package org.maoco.milyoner.gameplay.data.repository;

import org.maoco.milyoner.gameplay.data.entity.GameEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends CrudRepository<GameEntity, String> {
}
