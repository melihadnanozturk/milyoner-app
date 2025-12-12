package org.maoco.milyoner.gameplay.service;

import org.maoco.milyoner.common.exception.NotFoundException;
import org.maoco.milyoner.gameplay.data.entity.GameEntity;
import org.maoco.milyoner.gameplay.data.repository.GameRepository;
import org.maoco.milyoner.gameplay.domain.Game;
import org.springframework.stereotype.Service;

@Service
public class GamePersistenceService {

    private final GameRepository gameRepository;

    public GamePersistenceService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public GameEntity createNewUser(Game game) {
        GameEntity entity = new GameEntity(game.getGameId(), game.getUsername(), game.getQuestionLevel(), game.getGameState());
        return gameRepository.save(entity);
    }

    public GameEntity findById(String id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found by id : " + id));
    }

    public GameEntity saveGamer(GameEntity gameEntity) {
        return gameRepository.save(gameEntity);
    }

    public GameEntity findByUsername(String username) {
        return gameRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found by id : " + username));
    }

    public GameEntity findByUsernameAndId(String username, String id) {
        return gameRepository.findByUsernameAndId(username, id)
                .orElseThrow(() -> new NotFoundException("User not found by id and username : " + id + username));
    }
}
