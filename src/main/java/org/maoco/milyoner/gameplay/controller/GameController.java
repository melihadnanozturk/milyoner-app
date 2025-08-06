package org.maoco.milyoner.gameplay.controller;

import lombok.RequiredArgsConstructor;
import org.maoco.milyoner.common.ApiResponse;
import org.maoco.milyoner.gameplay.controller.request.GameActionRequest;
import org.maoco.milyoner.gameplay.controller.request.GameQuestionAnswerRequest;
import org.maoco.milyoner.gameplay.controller.request.GameQuestionQueryRequest;
import org.maoco.milyoner.gameplay.controller.request.StartGameRequest;
import org.maoco.milyoner.gameplay.controller.response.*;
import org.maoco.milyoner.gameplay.model.Game;
import org.maoco.milyoner.gameplay.model.Question;
import org.maoco.milyoner.gameplay.service.GameService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/game")
@RestController
@RequiredArgsConstructor
public class GameController {

    private final GameService service;

    //TODO : CheckUser ?? / StartingGame
    @PostMapping("/start")
    public ApiResponse<StartGameResponse> startGame(@RequestBody StartGameRequest request) {
        Game game = service.startGame(request);

        return ApiResponse.success(StartGameResponse.builder()
                .gameStatus(game.getGameStatus())
                .questionLevel(game.getQuestionLevel())
                .gameId(game.getGameId())
                .playerId(game.getPlayerId())
                .build());
    }

    //TODO : GetQuestionByLevel
    @GetMapping("/questions")
    public ApiResponse<GameQuestionQueryResponse> getQuestion(@RequestBody GameQuestionQueryRequest request) {
        Question question = service.getQuestion(request);

        GameQuestionQueryResponse data = GameQuestionQueryResponse.builder()
                .game(GameResponse.builder()
                        .gameStatus(request.getGameStatus())
                        .gameId(request.getGameId())
                        .playerId(request.getPlayerId())
                        .questionLevel(request.getQuestionLevel())
                        .build())
                .question(QuestionResponse.builder()
                        .questionText(question.getQuestionText())
                        .answers(question.getAnswers())
                        .build())
                .build();

        return ApiResponse.success(data);
    }

    //TODO : Response nasıl olmalı ? / Genel bir response'mı yoksa özel bir response'mı ?
    //TODO : SetQuestion And CheckAnswer, NextLevel-Won || Lose
    @PostMapping("/questions")
    public ApiResponse<GameResponse> setAnswer(@RequestBody GameQuestionAnswerRequest request) {
        service.checkAnswer(request);
        return ApiResponse.success(null);
    }


    @PostMapping("/action")
    public ApiResponse<GameStateResponse> handleAction(GameActionRequest request) {
        return null;
    }
}
