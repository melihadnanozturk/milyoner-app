package org.maoco.milyoner.gameplay.service.impl;

import com.google.common.hash.Hashing;
import lombok.RequiredArgsConstructor;
import org.maoco.milyoner.common.exception.WrongAnswerException;
import org.maoco.milyoner.gameplay.controller.request.GameQuestionAnswerRequest;
import org.maoco.milyoner.gameplay.controller.request.GameQuestionQueryRequest;
import org.maoco.milyoner.gameplay.controller.request.StartGameRequest;
import org.maoco.milyoner.gameplay.controller.response.GameResponse;
import org.maoco.milyoner.gameplay.model.Game;
import org.maoco.milyoner.gameplay.model.GameStatus;
import org.maoco.milyoner.gameplay.model.Question;
import org.maoco.milyoner.gameplay.service.GameService;
import org.maoco.milyoner.question.controller.inside.service.InsQuestionService;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final InsQuestionService questionService;

    @Override
    public Game startGame(StartGameRequest request) {

        //TODO : KC'ye gidilebilir ?
        String gameId = UUID.randomUUID().toString();
        String playerId = gameId + request.getUsername();

        String hashedGameId = this.convertStringToHash(gameId);
        String hashedPlayerId = this.convertStringToHash(playerId);

        return Game.builder()
                .playerId(hashedPlayerId)
                .gameId(hashedGameId)
                .gameStatus(GameStatus.START_GAME)
                .build();
    }


    //TODO : Burada paket'e özel olan bir domaine çevirip, buradan api ye cevap dönmesi gerekir.
    //TODO : Level set edilir mi ?.
    @Override
    public Question getQuestion(GameQuestionQueryRequest request) {
        this.checkGameStatus(request);

        var data = questionService.getQuestion(request.getQuestionLevel());

        return Question.of(data);
    }

    @Override
    public GameResponse checkAnswer(GameQuestionAnswerRequest request) {
        var data = questionService.handleAnswer(request.getQuestionId(), request.getAnswerId());

        if (!data.getIsCorrect().equals(true)) {
            throw new WrongAnswerException();
        }

        //todo : burada oyun statusu değişecek
        return null;
    }

    private String convertStringToHash(String string) {
        return Hashing.sha256()
                .hashString(string, StandardCharsets.UTF_8)
                .toString();
    }

    //TODO : WON ve QUIT durumunu da burada yazabilirsin :)
    //TODO : WON ve QUIT farklı endpoint üzerinden de kontrol edebilirsin
    //TODO : Burada hashKontrolu de yapılması gerekebilir
    private void checkGameStatus(GameQuestionQueryRequest request) {
        var status = request.getGameStatus();

        switch (status) {
            case START_GAME -> {
                request.setQuestionLevel(1L);
                request.setGameStatus(GameStatus.IN_PROGRESS);
            }
            case IN_PROGRESS -> request.setQuestionLevel(request.getQuestionLevel() + 1L);

            // todo: duzenlenecek
            default -> throw new IllegalStateException("Unexpected value: " + status);
        }
    }

    private void changeGameInfos(GameQuestionAnswerRequest request, GameStatus status) {

    }
}

