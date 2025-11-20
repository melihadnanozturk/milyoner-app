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

    public Game checkAnswer(GameQuestionAnswerRequest request) {
        Game game = Game.buildGameFromRequest(request);
        Boolean data = isAnswerCorrect(request.getAnswerId(), request.getQuestionId());

        //todo: oyuncu db de update edilecek

        if (!data.equals(true)) {
            game.updateGameState(GameStateEnum.LOST);
        }

        if (game.getQuestionLevel() == 10L) {
            game.updateGameState(GameStateEnum.WON);
        }

        game.setQuestionLevel(game.getQuestionLevel() + 1);

        return game;
    }

    private Boolean isAnswerCorrect(Long answerId, Long questionId) {
        AnswerEntity answerEntity = questionQueryService.handleAnswer(questionId, answerId);
        return answerEntity.getIsCorrect();
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
        GameStateEnum gameState = GameStateEnum.START_GAME;
        Long questionLevel = 1L;

        GamerEntity newUser = gamerService.createNewUser(new Gamer(request.getUsername(),
                hashedPlayerId,
                hashedGameId,
                questionLevel,
                gameState));

        return Game.builder()
                .playerId(newUser.getId())
                .gameId(newUser.getGameId())
                .gameState(newUser.getGameState())
                .questionLevel(newUser.getQuestionLevel())
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

    public UserScore getResult(GameRequest request) {
        GamerEntity gamerEntity = gamerService.findById(request.getPlayerId());

        UserScore userScore = new UserScore(gamerEntity.getUsername(), gamerEntity.getQuestionLevel());

        if (gamerEntity.getGameState() == GameStateEnum.WON) {
            userScore.setMessage("OYUNU KAZANDINIZ");
            return userScore;
        } else if (gamerEntity.getGameState() == GameStateEnum.LOST) {
            userScore.setMessage("OYUNU KAYBETTİNİZ");
            return userScore;
        }

        //todo: kullanıcı oyundan mı çıktı ? Yanlis mi cevap verdi ? Bunun kontrolü eklenebilir
        throw new RuntimeException("KULLANICI ÇIKIŞ YAPTI :O");
    }

    private String convertStringToHash(String string) {
        return Hashing.sha256()
                .hashString(string, StandardCharsets.UTF_8)
                .toString();
    }
}
