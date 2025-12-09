package org.maoco.milyoner.question.data.repository;

import org.maoco.milyoner.question.data.entity.AdminEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AdminRepository extends CrudRepository<AdminEntity, Long> {
    Optional<AdminEntity> findByUsername(String username);
}
