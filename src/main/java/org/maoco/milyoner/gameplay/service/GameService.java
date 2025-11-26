package org.maoco.milyoner.gameplay.service;

import com.google.common.hash.Hashing;
import jakarta.validation.Valid;
import org.maoco.milyoner.common.error.GamePlayError;
import org.maoco.milyoner.common.exception.AnswerException;
import org.maoco.milyoner.common.exception.MilyonerException;
import org.maoco.milyoner.common.exception.NotFoundException;
import org.maoco.milyoner.gameplay.data.entity.GamerEntity;
import org.maoco.milyoner.gameplay.domain.*;
import org.maoco.milyoner.gameplay.web.dto.request.GameQuestionAnswerRequest;
import org.maoco.milyoner.gameplay.web.dto.request.GameQuestionQueryRequest;
import org.maoco.milyoner.gameplay.web.dto.request.GameRequest;
import org.maoco.milyoner.gameplay.web.dto.request.StartGameRequest;
import org.maoco.milyoner.question.data.entity.AnswerEntity;
import org.maoco.milyoner.question.service.QuestionQueryService;
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
    private final QuestionQueryService questionQueryService;
    private final GamerService gamerService;


    public GameService(InsQuestionService insQuestionService, QuestionQueryService questionQueryService, GamerService gamerService, GamerService gamerService1) {
        this.insQuestionService = insQuestionService;
        this.questionQueryService = questionQueryService;
        this.gamerService = gamerService1;
    }

    public Game startGame(@Valid StartGameRequest request) {
        String gameId = UUID.randomUUID().toString();
        String playerId = gameId + request.getUsername();

        String hashedGameId = this.convertStringToHash(gameId);
        String hashedPlayerId = this.convertStringToHash(playerId);
        GameStateEnum gameState = GameStateEnum.START_GAME;

        GamerEntity newUser = gamerService.createNewUser(new Gamer(request.getUsername(),
                hashedPlayerId,
                hashedGameId,
                0L,
                gameState));

        return Game.builder()
                .playerId(newUser.getId())
                .gameId(newUser.getGameId())
                .gameState(newUser.getGameState())
                .questionLevel(newUser.getQuestionLevel())
                .build();
    }

    public Game getQuestions(GameQuestionQueryRequest request) {
        GamerEntity gamerEntity = checkUser(request.getPlayerId());
        var data = insQuestionService.getQuestion(gamerEntity.getQuestionLevel());

        Question question = Question.of(data);
        List<Answer> answers = question.getAnswers();
        List<Answer> activateAnswers = answers.stream().filter(Answer::getIsActivate)
                .collect(Collectors.collectingAndThen(Collectors.toList(),
                        list -> {
                            if (list.size() < WRONG_ANSWER_LIMITS + 1) {
                                throw new NotFoundException("There is not enough answer in question with questionId: " + question.getQuestionId());
                            }
                            return list;
                        }));

        /*todo : AI'a sor => Veriler db de yer alıyor, dışarıdan yanlış bir bilgi eklenmesi söz konus olmaza (@Valid).
         *  DBden gelen bilgileri yine de kontrol etmek gerekir mi ? Alttaki gibi kontrol sağlamak gerekir mi ?  */
        List<Answer> correctAnswer = activateAnswers.stream().filter(Answer::getIsCorrect)
                .findFirst()
                .map(List::of)
                //todo : new exception
                .orElseThrow(() -> new NotFoundException("There is no enough correct answer in question questionId: " + question.getQuestionId()));

        List<Answer> wrongAnswers = activateAnswers.stream()
                .filter(answer -> !answer.getIsCorrect())
                .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
                            if (list.size() < WRONG_ANSWER_LIMITS) {
                                new NotFoundException("There is not enough wrong answer in question with questionId: " + question.getQuestionId());
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

        Game game = Game.buildGameFromGamerEntity(gamerEntity);
        game.setQuestion(question);

        return game;
    }

    public Game checkAnswer(GameQuestionAnswerRequest request) {
        GamerEntity gamerEntity = gamerService.findById(request.getPlayerId());
        Boolean data = isAnswerCorrect(request.getAnswerId(), request.getQuestionId());

        if (gamerEntity.getGameState() != GameStateEnum.IN_PROGRESS) {
            throw new MilyonerException(GamePlayError.INCORRECT_STATUS);
        }

        if (!data.equals(true)) {
            gamerEntity.setGameState(GameStateEnum.LOST);
            GamerEntity updatedGamer = gamerService.saveGamer(gamerEntity);

            return Game.buildGameFromGamerEntity(updatedGamer);
        }

        if (gamerEntity.getQuestionLevel() == 10L) {
            gamerEntity.setGameState(GameStateEnum.WON);

            GamerEntity updatedGamer = gamerService.saveGamer(gamerEntity);
            return Game.buildGameFromGamerEntity(updatedGamer);
        }

        gamerEntity.setQuestionLevel(gamerEntity.getQuestionLevel() + 1);
        GamerEntity updatedGamer = gamerService.saveGamer(gamerEntity);

        return Game.buildGameFromGamerEntity(updatedGamer);

    }

    private Boolean isAnswerCorrect(Long answerId, Long questionId) {
        AnswerEntity answerEntity = questionQueryService.handleAnswer(questionId, answerId);
        return answerEntity.getIsCorrect();
    }

    public UserScore getResult(GameRequest request) {
        GamerEntity gamerEntity = gamerService.findById(request.getPlayerId());

        UserScore userScore = new UserScore(gamerEntity.getUsername(), gamerEntity.getQuestionLevel(),gamerEntity.getGameState());

        if (gamerEntity.getGameState() == GameStateEnum.WON) {
            userScore.setMessage("OYUNU KAZANDINIZ");
            return userScore;
        } else if (gamerEntity.getGameState() == GameStateEnum.LOST) {
            userScore.setMessage("OYUNU KAYBETTİNİZ");
            return userScore;
        }

        //todo: kullanıcı oyundan mı çıktı ? Yanlis mi cevap verdi ? Bunun kontrolü eklenebilir
        throw new MilyonerException(GamePlayError.INCORRECT_STATUS);
    }

    private GamerEntity checkUser(String id) {
        GamerEntity gamerEntity = gamerService.findById(id);

        if (gamerEntity.getGameState() == GameStateEnum.START_GAME) {
            gamerEntity.setGameState(GameStateEnum.IN_PROGRESS);
            gamerEntity.setQuestionLevel(1L);
            return gamerService.saveGamer(gamerEntity);
        }

        if (gamerEntity.getGameState() != GameStateEnum.IN_PROGRESS) {
            throw new MilyonerException(GamePlayError.INCORRECT_STATUS);
        }

        return gamerEntity;
    }

    private String convertStringToHash(String string) {
        return Hashing.sha256()
                .hashString(string, StandardCharsets.UTF_8)
                .toString();
    }
}
