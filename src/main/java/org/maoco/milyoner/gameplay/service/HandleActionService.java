package org.maoco.milyoner.gameplay.service;

import com.google.common.hash.Hashing;
import lombok.RequiredArgsConstructor;
import org.maoco.milyoner.gameplay.domain.Game;
import org.maoco.milyoner.gameplay.service.handler.GameStateEnum;
import org.maoco.milyoner.gameplay.service.handler.GameStateFactory;
import org.maoco.milyoner.gameplay.web.dto.request.GameQuestionQueryRequest;
import org.maoco.milyoner.gameplay.web.dto.request.StartGameRequest;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HandleActionService {

    private final GameStateFactory gameStateFactory;

    //todo: burayı kaldırıp direkt olarak tüm işlemleri tek bir service methodu üzerinden yapabiliriz çünkü
    // frontend tarafından startGame adından bir endpoint lazım olmayabilir.
    // Geri kalan işlemler için tek bir endpoin yazmıştık zaten. (Düşünülüp tartılabilir)

    //todo: burası direkt olarak stateGameProcess'e taşınması mantıklı
    public Game startGame(StartGameRequest request) {

        //TODO : KC'ye gidilebilir ?
        String gameId = UUID.randomUUID().toString();
        String playerId = gameId + request.getUsername();

        String hashedGameId = this.convertStringToHash(gameId);
        String hashedPlayerId = this.convertStringToHash(playerId);

        Game game = Game.builder()
                .playerId(hashedPlayerId)
                .gameId(hashedGameId)
                .gameState(GameStateEnum.START_GAME)
                .build();

        return handleAction(game);
    }

    public Game handleAction(GameQuestionQueryRequest request) {
        Game game = Game.buildGameFromRequest(request);

        return handleAction(game);
    }

    public Game handleAction(Game game) {
        return gameStateFactory.process(game);
    }

    private String convertStringToHash(String string) {
        return Hashing.sha256()
                .hashString(string, StandardCharsets.UTF_8)
                .toString();
    }

    //TODO : WON ve QUIT durumunu da burada yazabilirsin :)
    //TODO : WON ve QUIT farklı endpoint üzerinden de kontrol edebilirsin
    //TODO : Burada hashKontrolu de yapılması gerekebilir
    private void checkGameStatus(GameStateEnum status) {

        switch (status) {
            case WON -> throw new IllegalStateException("Unexpected value: " + status);

            case QUIT -> throw new IllegalStateException("Unexpected value: " + status);

            case LOST -> throw new IllegalStateException("Unexpected value: " + status);
            default -> {//null
            }
        }
    }


}

