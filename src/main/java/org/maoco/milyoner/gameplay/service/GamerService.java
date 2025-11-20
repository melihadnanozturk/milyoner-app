package org.maoco.milyoner.gameplay.service;

import org.maoco.milyoner.common.exception.NotFoundException;
import org.maoco.milyoner.gameplay.data.entity.GamerEntity;
import org.maoco.milyoner.gameplay.data.repository.UserRepository;
import org.maoco.milyoner.gameplay.domain.Gamer;
import org.springframework.stereotype.Service;

@Service
public class GamerService {

    private final UserRepository userRepository;

    public GamerService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public GamerEntity createNewUser(Gamer gamer) {
        GamerEntity entity = new GamerEntity(gamer.getPlayerId(), gamer.getUsername(), gamer.getGameId(), gamer.getQuestionLevel(), gamer.getGameState());
        return userRepository.save(entity);
    }

    public GamerEntity findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found by id : " + id));
    }
}
