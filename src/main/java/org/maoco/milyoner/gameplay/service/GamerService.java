package org.maoco.milyoner.gameplay.service;

import org.maoco.milyoner.common.exception.NotFoundException;
import org.maoco.milyoner.gameplay.data.entity.GamerEntity;
import org.maoco.milyoner.gameplay.data.repository.GamerRepository;
import org.maoco.milyoner.gameplay.domain.Gamer;
import org.springframework.stereotype.Service;

@Service
public class GamerService {

    private final GamerRepository gamerRepository;

    public GamerService(GamerRepository gamerRepository) {
        this.gamerRepository = gamerRepository;
    }

    public GamerEntity createNewUser(Gamer gamer) {
        GamerEntity entity = new GamerEntity(gamer.getPlayerId(), gamer.getUsername(), gamer.getGameId(), gamer.getQuestionLevel(), gamer.getGameState());
        return gamerRepository.save(entity);
    }

    public GamerEntity findById(String id) {
        return gamerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found by id : " + id));
    }
}
