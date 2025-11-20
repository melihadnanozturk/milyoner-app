package org.maoco.milyoner.gameplay.data.repository;

import org.maoco.milyoner.gameplay.data.entity.GamerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GamerRepository extends CrudRepository<GamerEntity, String> {
}
