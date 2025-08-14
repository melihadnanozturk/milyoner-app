package org.maoco.milyoner.gameplay.service;

import com.google.common.hash.Hashing;
import lombok.RequiredArgsConstructor;
import org.maoco.milyoner.gameplay.domain.Game;
import org.maoco.milyoner.gameplay.domain.GamePhase;
import org.maoco.milyoner.gameplay.web.dto.request.GameQuestionAnswerRequest;
import org.maoco.milyoner.gameplay.web.dto.request.GameQuestionQueryRequest;
import org.maoco.milyoner.gameplay.web.dto.request.GameRequest;
import org.maoco.milyoner.gameplay.web.dto.request.StartGameRequest;
import org.maoco.milyoner.question.web.controller.port_in.service.InsQuestionService;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameStateFactory factory;
    private final InsQuestionService insQuestionService;

    public Game startGame(StartGameRequest request) {

        //TODO : KC'ye gidilebilir ?
        String gameId = UUID.randomUUID().toString();
        String playerId = gameId + request.getUsername();

        String hashedGameId = this.convertStringToHash(gameId);
        String hashedPlayerId = this.convertStringToHash(playerId);

        return Game.builder()
                .playerId(hashedPlayerId)
                .gameId(hashedGameId)
                .gamePhase(GamePhase.START_GAME)
                .build();
    }

    //todo: burada status kontrolu yapmaya gerek yok
    //todo: method ismi handleAction olarak değiştirebiliriz
    //todo: eğer hala tüm işlemler için ayrı ayrı endpoint oluşturulacaksa, service özelleştirikip, ilgili handler'a gidilmesi sağlanabilir
    public Game getQuestion(GameQuestionQueryRequest request) {
        this.checkGameStatus(request.getGamePhase());

        Game game = this.buildGameFromRequest(request);

        var handler = factory.getHandler(game.getGamePhase());
        return handler.execute(game);
    }

    public Game checkAnswer(GameQuestionAnswerRequest request) {
        Game game = this.buildGameFromRequest(request);
        var data = insQuestionService.handleAnswer(request.getAnswerId(), request.getQuestionId());


        if (!data.getIsCorrect().equals(true)) {
            game.updateGameState(GamePhase.LOST);
            return factory.getHandler(game.getGamePhase()).execute(game);
        }

        if (game.getQuestionLevel() == 10L) {
            game.updateGameState(GamePhase.WON);
            return factory.getHandler(game.getGamePhase()).execute(game);
        }

        game.setQuestionLevel(game.getQuestionLevel() + 1);


        return game;
    }

    private Game buildGameFromRequest(GameRequest request) {
        return Game.builder()
                .playerId(request.getPlayerId())
                .gameId(request.getGameId())
                .questionLevel(request.getQuestionLevel())
                .gamePhase(request.getGamePhase())
                .build();
    }

    private String convertStringToHash(String string) {
        return Hashing.sha256()
                .hashString(string, StandardCharsets.UTF_8)
                .toString();
    }

    //TODO : WON ve QUIT durumunu da burada yazabilirsin :)
    //TODO : WON ve QUIT farklı endpoint üzerinden de kontrol edebilirsin
    //TODO : Burada hashKontrolu de yapılması gerekebilir
    private void checkGameStatus(GamePhase status) {

        switch (status) {
            case WON -> throw new IllegalStateException("Unexpected value: " + status);

            case QUIT -> throw new IllegalStateException("Unexpected value: " + status);

            case LOST -> throw new IllegalStateException("Unexpected value: " + status);
            default -> {//null
            }
        }
    }


}

