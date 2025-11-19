package org.maoco.milyoner.gameplay.web.controller;

import jakarta.validation.Valid;
import org.maoco.milyoner.common.ApiResponse;
import org.maoco.milyoner.gameplay.domain.Game;
import org.maoco.milyoner.gameplay.service.HandleActionService;
import org.maoco.milyoner.gameplay.web.dto.request.GameActionRequest;
import org.maoco.milyoner.gameplay.web.dto.request.GameQuestionAnswerRequest;
import org.maoco.milyoner.gameplay.web.dto.request.GameQuestionQueryRequest;
import org.maoco.milyoner.gameplay.web.dto.request.StartGameRequest;
import org.maoco.milyoner.gameplay.web.dto.response.*;
import org.maoco.milyoner.question.web.controller.port_in.service.InsQuestionService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/game")
@RestController
public class GameController {

    private final HandleActionService handleActionService;
    private final InsQuestionService insQuestionService;

    public GameController(HandleActionService handleActionService, InsQuestionService insQuestionService) {
        this.handleActionService = handleActionService;
        this.insQuestionService = insQuestionService;
    }

    //TODO : CheckUser ?? / StartingGame
    @PostMapping("/start")
    public ApiResponse<StartGameResponse> startGame(@RequestBody @Valid StartGameRequest request) {
        Game game = handleActionService.startGame(request);

        return ApiResponse.success(StartGameResponse.builder()
                .gameState(game.getGameState())
                .questionLevel(game.getQuestionLevel())
                .gameId(game.getGameId())
                .playerId(game.getPlayerId())
                .build());
    }

    @GetMapping("/questions")
    public ApiResponse<GameQuestionQueryResponse> getQuestion(@RequestBody GameQuestionQueryRequest request) {
        Game game = handleActionService.handleAction(request);

        GameQuestionQueryResponse data = GameQuestionQueryResponse.builder()
                .game(GameResponse.builder()
                        .gameState(game.getGameState())
                        .gameId(game.getGameId())
                        .playerId(game.getPlayerId())
                        .questionLevel(game.getQuestionLevel())
                        .build())
                .question(QuestionResponse.builder()
                        .questionId(game.getQuestion().getQuestionId())
                        .questionText(game.getQuestion().getQuestionText())
                        .answers(game.getQuestion().getAnswers().stream().map(answer -> AnswerResponse.builder()
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

        Game game = insQuestionService.checkAnswer(request);

        return ApiResponse.success(GameResponse.builder()
                .questionLevel(game.getQuestionLevel())
                .playerId(game.getPlayerId())
                .gameId(game.getGameId())
                .gameState(game.getGameState())
                .build());
    }


    @PostMapping("/action")
    public ApiResponse<GameStateResponse> handleAction(GameActionRequest request) {
        return null;
    }
}
