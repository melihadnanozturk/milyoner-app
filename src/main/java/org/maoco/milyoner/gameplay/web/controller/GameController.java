package org.maoco.milyoner.gameplay.web.controller;

import jakarta.validation.Valid;
import org.maoco.milyoner.common.ApiResponse;
import org.maoco.milyoner.gameplay.domain.Game;
import org.maoco.milyoner.gameplay.domain.UserScore;
import org.maoco.milyoner.gameplay.service.GameService;
import org.maoco.milyoner.gameplay.web.dto.request.GameQuestionAnswerRequest;
import org.maoco.milyoner.gameplay.web.dto.request.GameQuestionQueryRequest;
import org.maoco.milyoner.gameplay.web.dto.request.GameRequest;
import org.maoco.milyoner.gameplay.web.dto.request.StartGameRequest;
import org.maoco.milyoner.gameplay.web.dto.response.*;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/game")
@RestController
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    //TODO : CheckUser ?? / StartingGame
    @PostMapping("/start")
    public ApiResponse<StartGameResponse> startGame(@RequestBody @Valid StartGameRequest request) {
        Game game = gameService.startGame(request);

        return ApiResponse.success(StartGameResponse.builder()
                .gameState(game.getGameState())
                .questionLevel(game.getQuestionLevel())
                .gameId(game.getGameId())
                .playerId(game.getPlayerId())
                .build());
    }

    @GetMapping("/questions")
    public ApiResponse<GameQuestionQueryResponse> getQuestions(@RequestBody GameQuestionQueryRequest request) {
        Game game = gameService.getQuestions(request);

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

    @PostMapping("/questions")
    public ApiResponse<GameResponse> setAnswer(@RequestBody GameQuestionAnswerRequest request) {
        Game game = gameService.checkAnswer(request);

        return ApiResponse.success(GameResponse.builder()
                .questionLevel(game.getQuestionLevel())
                .playerId(game.getPlayerId())
                .gameId(game.getGameId())
                .gameState(game.getGameState())
                .build());
    }

    @PostMapping("/result")
    public ApiResponse<UserScoreResponse> getGameResult(@RequestBody GameRequest request) {
        UserScore userScore = gameService.getResult(request);

        return ApiResponse.success(UserScoreResponse.builder()
                .score(userScore.getScore())
                .message(userScore.getMessage())
                .username(userScore.getUsername())
                .build());
    }

}
