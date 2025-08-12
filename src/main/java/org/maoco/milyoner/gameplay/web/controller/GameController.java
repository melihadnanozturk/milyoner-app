package org.maoco.milyoner.gameplay.web.controller;

import jakarta.validation.Valid;
import org.maoco.milyoner.common.ApiResponse;
import org.maoco.milyoner.gameplay.web.dto.request.GameActionRequest;
import org.maoco.milyoner.gameplay.web.dto.request.GameQuestionAnswerRequest;
import org.maoco.milyoner.gameplay.web.dto.request.GameQuestionQueryRequest;
import org.maoco.milyoner.gameplay.web.dto.request.StartGameRequest;
import org.maoco.milyoner.gameplay.domain.Game;
import org.maoco.milyoner.gameplay.domain.Question;
import org.maoco.milyoner.gameplay.service.GameService;
import org.maoco.milyoner.gameplay.web.dto.response.*;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/game")
@RestController
public class GameController {

    private final GameService service;

    public GameController(GameService gameService) {
        this.service = gameService;
    }

    //TODO : CheckUser ?? / StartingGame
    @PostMapping("/start")
    public ApiResponse<StartGameResponse> startGame(@RequestBody @Valid StartGameRequest request) {
        Game game = service.startGame(request);

        return ApiResponse.success(StartGameResponse.builder()
                .gameStatus(game.getGameStatus())
                .questionLevel(game.getQuestionLevel())
                .gameId(game.getGameId())
                .playerId(game.getPlayerId())
                .build());
    }

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
                        .questionId(question.getQuestionId())
                        .questionText(question.getQuestionText())
                        .answers(question.getAnswers().stream().map(answer -> AnswerResponse.builder()
                                .id(answer.getId())
                                .text(answer.getAnswerText())
                                .build()).toList())
                        .build())
                .build();

        return ApiResponse.success(data);
    }

    //TODO : Response nasıl olmalı ? / Genel bir response'mı yoksa özel bir response'mı ?
    //TODO : SetQuestion And CheckAnswer, NextLevel-Won || Lose
    @PostMapping("/questions")
    public ApiResponse<GameResponse> setAnswer(@RequestBody GameQuestionAnswerRequest request) {
        Game game = service.checkAnswer(request);

        return ApiResponse.success(GameResponse.builder()
                .questionLevel(game.getQuestionLevel())
                .playerId(game.getPlayerId())
                .gameId(game.getGameId())
                .gameStatus(game.getGameStatus())
                .build());
    }


    @PostMapping("/action")
    public ApiResponse<GameStateResponse> handleAction(GameActionRequest request) {
        return null;
    }
}
