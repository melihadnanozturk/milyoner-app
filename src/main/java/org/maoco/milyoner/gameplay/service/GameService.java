package org.maoco.milyoner.gameplay.service;

import com.google.common.hash.Hashing;
import jakarta.validation.Valid;
import org.maoco.milyoner.common.error.GamePlayError;
import org.maoco.milyoner.common.exception.AnswerException;
import org.maoco.milyoner.common.exception.MilyonerException;
import org.maoco.milyoner.common.exception.NotFoundException;
import org.maoco.milyoner.gameplay.domain.Answer;
import org.maoco.milyoner.gameplay.domain.Game;
import org.maoco.milyoner.gameplay.domain.Question;
import org.maoco.milyoner.gameplay.service.handler.GameStateEnum;
import org.maoco.milyoner.gameplay.web.dto.request.GameQuestionAnswerRequest;
import org.maoco.milyoner.gameplay.web.dto.request.GameQuestionQueryRequest;
import org.maoco.milyoner.gameplay.web.dto.request.GameRequest;
import org.maoco.milyoner.gameplay.web.dto.request.StartGameRequest;
import org.maoco.milyoner.question.data.entity.AnswerEntity;
import org.maoco.milyoner.question.web.controller.port_in.dto.response.InsAnswerResponse;
import org.maoco.milyoner.question.web.controller.port_in.service.InsQuestionService;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GameService {

    private final InsQuestionService insQuestionService;

    private final int WRONG_ANSWER_LIMITS = 3;


    public GameService(InsQuestionService insQuestionService) {
        this.insQuestionService = insQuestionService;
    }

    public Game checkAnswer(GameQuestionAnswerRequest request) {
        Game game = Game.buildGameFromRequest(request);
        var data = handleAnswer(request.getAnswerId(), request.getQuestionId());


        if (!data.getIsCorrect().equals(true)) {
            game.updateGameState(GameStateEnum.LOST);
        }

        if (game.getQuestionLevel() == 10L) {
            game.updateGameState(GameStateEnum.WON);
        }

        game.setQuestionLevel(game.getQuestionLevel() + 1);

        return game;
    }

    private InsAnswerResponse handleAnswer(Long answerId, Long questionId) {
//        AnswerEntity answerEntity = queryService.handleAnswer(questionId, answerId);
        AnswerEntity answerEntity = null;
        return InsAnswerResponse.builder()
                .isCorrect(answerEntity.getIsCorrect())
                .answerId(answerEntity.getId())
                .answerText(answerEntity.getAnswerText())
                .build();
    }

    public Game won(Game game) {
        return Game.builder()
                .gameId("deneme")
                .gameState(GameStateEnum.WON)
                .playerId("PLAYER_ID")
                .questionLevel(10L)
                .build();
    }

    public Game lost(Game game) {
        throw new MilyonerException(GamePlayError.WRONG_ANSWER, "KAYBETTIN");

    }

    public Game quit(Game game) {
        throw new MilyonerException(GamePlayError.WRONG_ANSWER, "CIKTIN, DAHA KARPUZ KESECEKTİK ;(");

    }

    public Game startGame(@Valid StartGameRequest request) {
        String gameId = UUID.randomUUID().toString();
        String playerId = gameId + request.getUsername();

        String hashedGameId = this.convertStringToHash(gameId);
        String hashedPlayerId = this.convertStringToHash(playerId);

        return Game.builder()
                .playerId(hashedPlayerId)
                .gameId(hashedGameId)
                .gameState(GameStateEnum.START_GAME)
                .questionLevel(1L)
                .build();
    }

    public Game getQuestions(GameQuestionQueryRequest request) {
        Game game = Game.buildGameFromRequest(request);
        var data = insQuestionService.getQuestion(game.getQuestionLevel());

        Question question = Question.of(data);
        List<Answer> answers = question.getAnswers();
        List<Answer> activateAnswers = answers.stream().filter(Answer::getIsActivate)
                .collect(Collectors.collectingAndThen(Collectors.toList(),
                        list -> {
                            if (list.size() < WRONG_ANSWER_LIMITS + 1) {
                                throw new AnswerException("There is not enough answer in question with questionId: " + question.getQuestionId());
                            }
                            return list;
                        }));

        /*todo : AI'a sor => Veriler db de yer alıyor, dışarıdan yanlış bir bilgi eklenmesi söz konus olmaza (@Valid).
         *  DBden gelen bilgileri yine de kontrol etmek gerekir mi ? Alttaki gibi kontrol sağlamak gerekir mi ?  */
        List<Answer> correctAnswer = activateAnswers.stream().filter(Answer::getIsCorrect)
                .findFirst()
                .map(List::of)
                //todo : new exception
                .orElseThrow(() -> new NotFoundException("There is no correct answer in question with level: " + game.getQuestionLevel()));

        List<Answer> wrongAnswers = activateAnswers.stream()
                .filter(answer -> !answer.getIsCorrect())
                .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
                            if (list.size() < WRONG_ANSWER_LIMITS) {
                                new AnswerException("There is not enough wrong answer in question with questionId: " + question.getQuestionId());
                            }
                            Collections.shuffle(list);
                            return list.stream().limit(WRONG_ANSWER_LIMITS).collect(Collectors.toList());
                        }
                ));

        List finalAnswers = new ArrayList();
        finalAnswers.addAll(correctAnswer);
        finalAnswers.addAll(wrongAnswers);
        Collections.shuffle(finalAnswers);
        question.setAnswers(finalAnswers);

        game.setQuestion(question);

        return game;
    }

    public Game getResult(GameRequest request) {
        return null;
    }

    private String convertStringToHash(String string) {
        return Hashing.sha256()
                .hashString(string, StandardCharsets.UTF_8)
                .toString();
    }
}
